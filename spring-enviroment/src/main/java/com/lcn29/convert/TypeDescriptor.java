package com.lcn29.convert;

import java.io.Serializable;
import java.lang.annotation.Annotation;
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


	public static TypeDescriptor valueOf( Class<?> type) {
		if (type == null) {
			type = Object.class;
		}
		TypeDescriptor desc = commonTypesCache.get(type);
		return (desc != null ? desc : new TypeDescriptor(ResolvableType.forClass(type), null, null));
	}
}
