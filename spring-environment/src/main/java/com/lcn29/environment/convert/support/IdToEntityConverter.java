package com.lcn29.environment.convert.support;

import com.lcn29.environment.convert.ConversionService;
import com.lcn29.environment.convert.TypeDescriptor;
import com.lcn29.environment.convert.converter.ConditionalGenericConverter;
import com.lcn29.environment.util.ClassUtils;
import com.lcn29.environment.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:05
 */
final class IdToEntityConverter implements ConditionalGenericConverter {

    private final ConversionService conversionService;


    public IdToEntityConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Object.class, Object.class));
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        Method finder = getFinder(targetType.getType());
        return (finder != null &&
                this.conversionService.canConvert(sourceType, TypeDescriptor.valueOf(finder.getParameterTypes()[0])));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        Method finder = getFinder(targetType.getType());
        Object id = this.conversionService.convert(
                source, sourceType, TypeDescriptor.valueOf(finder.getParameterTypes()[0]));
        return ReflectionUtils.invokeMethod(finder, source, id);
    }

    private Method getFinder(Class<?> entityClass) {
        String finderMethod = "find" + getEntityName(entityClass);
        Method[] methods;
        boolean localOnlyFiltered;
        try {
            methods = entityClass.getDeclaredMethods();
            localOnlyFiltered = true;
        }
        catch (SecurityException ex) {
            // Not allowed to access non-public methods...
            // Fallback: check locally declared public methods only.
            methods = entityClass.getMethods();
            localOnlyFiltered = false;
        }
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers()) && method.getName().equals(finderMethod)
                    && method.getParameterCount() == 1
                    && method.getReturnType().equals(entityClass)
                    && (localOnlyFiltered || method.getDeclaringClass().equals(entityClass))) {
                return method;
            }
        }
        return null;
    }

    private String getEntityName(Class<?> entityClass) {
        String shortName = ClassUtils.getShortName(entityClass);
        int lastDot = shortName.lastIndexOf('.');
        if (lastDot != -1) {
            return shortName.substring(lastDot + 1);
        }
        else {
            return shortName;
        }
    }
}
