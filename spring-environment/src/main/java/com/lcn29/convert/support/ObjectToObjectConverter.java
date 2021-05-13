package com.lcn29.convert.support;

import com.lcn29.convert.TypeDescriptor;
import com.lcn29.convert.converter.ConditionalGenericConverter;
import com.lcn29.exception.ConversionFailedException;
import com.lcn29.util.ClassUtils;
import com.lcn29.util.ConcurrentReferenceHashMap;
import com.lcn29.util.ReflectionUtils;

import java.lang.reflect.*;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 21:59
 */
final class ObjectToObjectConverter implements ConditionalGenericConverter {

    private static final Map<Class<?>, Member> conversionMemberCache = new ConcurrentReferenceHashMap<>(32);

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Object.class, Object.class));
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return (sourceType.getType() != targetType.getType() &&
                hasConversionMethodOrConstructor(targetType.getType(), sourceType.getType()));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        Class<?> sourceClass = sourceType.getType();
        Class<?> targetClass = targetType.getType();
        Member member = getValidatedMember(targetClass, sourceClass);

        try {
            if (member instanceof Method) {
                Method method = (Method) member;
                ReflectionUtils.makeAccessible(method);
                if (!Modifier.isStatic(method.getModifiers())) {
                    return method.invoke(source);
                }
                else {
                    return method.invoke(null, source);
                }
            }
            else if (member instanceof Constructor) {
                Constructor<?> ctor = (Constructor<?>) member;
                ReflectionUtils.makeAccessible(ctor);
                return ctor.newInstance(source);
            }
        }
        catch (InvocationTargetException ex) {
            throw new ConversionFailedException(sourceType, targetType, source, ex.getTargetException());
        }
        catch (Throwable ex) {
            throw new ConversionFailedException(sourceType, targetType, source, ex);
        }

        throw new IllegalStateException(String.format("No to%3$s() method exists on %1$s, " +
                        "and no static valueOf/of/from(%1$s) method or %3$s(%1$s) constructor exists on %2$s.",
                sourceClass.getName(), targetClass.getName(), targetClass.getSimpleName()));
    }

    static boolean hasConversionMethodOrConstructor(Class<?> targetClass, Class<?> sourceClass) {
        return (getValidatedMember(targetClass, sourceClass) != null);
    }

    private static Member getValidatedMember(Class<?> targetClass, Class<?> sourceClass) {
        Member member = conversionMemberCache.get(targetClass);
        if (isApplicable(member, sourceClass)) {
            return member;
        }

        member = determineToMethod(targetClass, sourceClass);
        if (member == null) {
            member = determineFactoryMethod(targetClass, sourceClass);
            if (member == null) {
                member = determineFactoryConstructor(targetClass, sourceClass);
                if (member == null) {
                    return null;
                }
            }
        }

        conversionMemberCache.put(targetClass, member);
        return member;
    }

    private static boolean isApplicable(Member member, Class<?> sourceClass) {
        if (member instanceof Method) {
            Method method = (Method) member;
            return (!Modifier.isStatic(method.getModifiers()) ?
                    ClassUtils.isAssignable(method.getDeclaringClass(), sourceClass) :
                    method.getParameterTypes()[0] == sourceClass);
        }
        else if (member instanceof Constructor) {
            Constructor<?> ctor = (Constructor<?>) member;
            return (ctor.getParameterTypes()[0] == sourceClass);
        }
        else {
            return false;
        }
    }

    private static Method determineToMethod(Class<?> targetClass, Class<?> sourceClass) {
        if (String.class == targetClass || String.class == sourceClass) {
            return null;
        }

        Method method = ClassUtils.getMethodIfAvailable(sourceClass, "to" + targetClass.getSimpleName());
        return (method != null && !Modifier.isStatic(method.getModifiers()) &&
                ClassUtils.isAssignable(targetClass, method.getReturnType()) ? method : null);
    }

    private static Method determineFactoryMethod(Class<?> targetClass, Class<?> sourceClass) {
        if (String.class == targetClass) {
            // Do not accept the String.valueOf(Object) method
            return null;
        }

        Method method = ClassUtils.getStaticMethod(targetClass, "valueOf", sourceClass);
        if (method == null) {
            method = ClassUtils.getStaticMethod(targetClass, "of", sourceClass);
            if (method == null) {
                method = ClassUtils.getStaticMethod(targetClass, "from", sourceClass);
            }
        }
        return method;
    }

    private static Constructor<?> determineFactoryConstructor(Class<?> targetClass, Class<?> sourceClass) {
        return ClassUtils.getConstructorIfAvailable(targetClass, sourceClass);
    }
}
