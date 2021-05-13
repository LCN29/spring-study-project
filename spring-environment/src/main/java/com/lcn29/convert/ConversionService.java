package com.lcn29.convert;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-11  9:14
 */
public interface ConversionService {

	boolean canConvert(Class<?> sourceType, Class<?> targetType);

	boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType);

	<T> T convert(Object source, Class<T> targetType);

	Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType);
}
