package com.lcn29.converter;

import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-10  18:05
 */
public interface GenericConverter {

	Set<ConvertiblePair> getConvertibleTypes();

	Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType);

	final class ConvertiblePair {

		private final Class<?> sourceType;

		private final Class<?> targetType;

		public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
			this.sourceType = sourceType;
			this.targetType = targetType;
		}
	}
}
