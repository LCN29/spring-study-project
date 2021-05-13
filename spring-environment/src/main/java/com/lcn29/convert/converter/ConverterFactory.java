package com.lcn29.convert.converter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-13  18:05
 */
public interface ConverterFactory<S, R> {

	<T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
