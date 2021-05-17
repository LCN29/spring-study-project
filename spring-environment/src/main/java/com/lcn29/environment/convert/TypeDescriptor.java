package com.lcn29.environment.convert;

import com.lcn29.environment.core.ResolvableType;
import com.lcn29.environment.util.ClassUtils;
import com.lcn29.environment.util.ObjectUtils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-10  18:07
 */
public class TypeDescriptor implements Serializable {

    private final Class<?> type;

    private static final Annotation[] EMPTY_ANNOTATION_ARRAY = new Annotation[0];

    private static final Map<Class<?>, TypeDescriptor> commonTypesCache = new HashMap<>(32);

    private static final Class<?>[] CACHED_COMMON_TYPES = {
            boolean.class, Boolean.class, byte.class, Byte.class, char.class, Character.class,
            double.class, Double.class, float.class, Float.class, int.class, Integer.class,
            long.class, Long.class, short.class, Short.class, String.class, Object.class};


    static {
        for (Class<?> preCachedClass : CACHED_COMMON_TYPES) {
            commonTypesCache.put(preCachedClass, valueOf(preCachedClass));
        }
    }

    private final ResolvableType resolvableType;

    private final AnnotatedElementAdapter annotatedElement;

    public TypeDescriptor(ResolvableType resolvableType, Class<?> type, Annotation[] annotations) {
        this.resolvableType = resolvableType;
        this.type = (type != null ? type : resolvableType.toClass());
        this.annotatedElement = new AnnotatedElementAdapter(annotations);
    }

    public static TypeDescriptor valueOf(Class<?> type) {
        if (type == null) {
            type = Object.class;
        }
        TypeDescriptor desc = commonTypesCache.get(type);
        return (desc != null ? desc : new TypeDescriptor(ResolvableType.forClass(type), null, null));
    }

    public ResolvableType getResolvableType() {
        return this.resolvableType;
    }

    public Class<?> getType() {
        return this.type;
    }

    public Class<?> getObjectType() {
        return ClassUtils.resolvePrimitiveIfNecessary(getType());
    }

    public boolean isAssignableTo(TypeDescriptor typeDescriptor) {
        boolean typesAssignable = typeDescriptor.getObjectType().isAssignableFrom(getObjectType());
        if (!typesAssignable) {
            return false;
        }
        if (isArray() && typeDescriptor.isArray()) {
            return isNestedAssignable(getElementTypeDescriptor(), typeDescriptor.getElementTypeDescriptor());
        } else if (isCollection() && typeDescriptor.isCollection()) {
            return isNestedAssignable(getElementTypeDescriptor(), typeDescriptor.getElementTypeDescriptor());
        } else if (isMap() && typeDescriptor.isMap()) {
            return isNestedAssignable(getMapKeyTypeDescriptor(), typeDescriptor.getMapKeyTypeDescriptor()) &&
                    isNestedAssignable(getMapValueTypeDescriptor(), typeDescriptor.getMapValueTypeDescriptor());
        } else {
            return true;
        }
    }

    public boolean isArray() {
        return getType().isArray();
    }

    public boolean isCollection() {
        return Collection.class.isAssignableFrom(getType());
    }

    public boolean isMap() {
        return Map.class.isAssignableFrom(getType());
    }

    public static TypeDescriptor forObject(Object source) {
        return (source != null ? valueOf(source.getClass()) : null);
    }

    public TypeDescriptor getMapKeyTypeDescriptor() {
        return getRelatedIfResolvable(this, getResolvableType().asMap().getGeneric(0));
    }

    public TypeDescriptor getMapKeyTypeDescriptor(Object mapKey) {
        return narrow(mapKey, getMapKeyTypeDescriptor());
    }

    public TypeDescriptor getMapValueTypeDescriptor() {
        return getRelatedIfResolvable(this, getResolvableType().asMap().getGeneric(1));
    }

    public TypeDescriptor getMapValueTypeDescriptor(Object mapValue) {
        return narrow(mapValue, getMapValueTypeDescriptor());
    }

    public TypeDescriptor getElementTypeDescriptor() {
        if (getResolvableType().isArray()) {
            return new TypeDescriptor(getResolvableType().getComponentType(), null, getAnnotations());
        }
        if (Stream.class.isAssignableFrom(getType())) {
            return getRelatedIfResolvable(this, getResolvableType().as(Stream.class).getGeneric(0));
        }
        return getRelatedIfResolvable(this, getResolvableType().asCollection().getGeneric(0));
    }

    public Annotation[] getAnnotations() {
        return this.annotatedElement.getAnnotations();
    }

    public boolean isPrimitive() {
        return getType().isPrimitive();
    }

    private static TypeDescriptor getRelatedIfResolvable(TypeDescriptor source, ResolvableType type) {
        if (type.resolve() == null) {
            return null;
        }
        return new TypeDescriptor(type, null, source.getAnnotations());
    }

    public TypeDescriptor elementTypeDescriptor(Object element) {
        return narrow(element, getElementTypeDescriptor());
    }

    public TypeDescriptor narrow(Object value) {
        if (value == null) {
            return this;
        }
        ResolvableType narrowed = ResolvableType.forType(value.getClass(), getResolvableType());
        return new TypeDescriptor(narrowed, value.getClass(), getAnnotations());
    }

    public static TypeDescriptor collection(Class<?> collectionType, TypeDescriptor elementTypeDescriptor) {
        if (!Collection.class.isAssignableFrom(collectionType)) {
            throw new IllegalArgumentException("Collection type must be a [java.util.Collection]");
        }
        ResolvableType element = (elementTypeDescriptor != null ? elementTypeDescriptor.resolvableType : null);
        return new TypeDescriptor(ResolvableType.forClassWithGenerics(collectionType, element), null, null);
    }

    private TypeDescriptor narrow(Object value, TypeDescriptor typeDescriptor) {
        if (typeDescriptor != null) {
            return typeDescriptor.narrow(value);
        }
        if (value != null) {
            return narrow(value);
        }
        return null;
    }

    private class AnnotatedElementAdapter implements AnnotatedElement, Serializable {

        private final Annotation[] annotations;

        public AnnotatedElementAdapter(Annotation[] annotations) {
            this.annotations = annotations;
        }

        @Override
        public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
            for (Annotation annotation : getAnnotations()) {
                if (annotation.annotationType() == annotationClass) {
                    return true;
                }
            }
            return false;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
            for (Annotation annotation : getAnnotations()) {
                if (annotation.annotationType() == annotationClass) {
                    return (T) annotation;
                }
            }
            return null;
        }

        @Override
        public Annotation[] getAnnotations() {
            return (this.annotations != null ? this.annotations : EMPTY_ANNOTATION_ARRAY);
        }

        @Override
        public Annotation[] getDeclaredAnnotations() {
            return getAnnotations();
        }

        public boolean isEmpty() {
            return ObjectUtils.isEmpty(this.annotations);
        }

        @Override
        public boolean equals(Object other) {
            return (this == other || (other instanceof AnnotatedElementAdapter && Arrays.equals(this.annotations, ((AnnotatedElementAdapter) other).annotations)));
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(this.annotations);
        }

        @Override
        public String toString() {
            return TypeDescriptor.this.toString();
        }

    }

    private boolean isNestedAssignable(TypeDescriptor nestedTypeDescriptor, TypeDescriptor otherNestedTypeDescriptor) {

        return (nestedTypeDescriptor == null || otherNestedTypeDescriptor == null ||
                nestedTypeDescriptor.isAssignableTo(otherNestedTypeDescriptor));
    }
}
