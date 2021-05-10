package com.lcn29.convert;

import com.lcn29.core.ResolvableType;
import com.lcn29.util.ObjectUtils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

	public TypeDescriptor(ResolvableType resolvableType,  Class<?> type,  Annotation[] annotations) {
		this.resolvableType = resolvableType;
		this.type = (type != null ? type : resolvableType.toClass());
		this.annotatedElement = new AnnotatedElementAdapter(annotations);
	}


	public static TypeDescriptor valueOf( Class<?> type) {
		if (type == null) {
			type = Object.class;
		}
		TypeDescriptor desc = commonTypesCache.get(type);
		return (desc != null ? desc : new TypeDescriptor(ResolvableType.forClass(type), null, null));
	}

	private class AnnotatedElementAdapter implements AnnotatedElement, Serializable {

		private final Annotation[] annotations;

		public AnnotatedElementAdapter( Annotation[] annotations) {
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
}
