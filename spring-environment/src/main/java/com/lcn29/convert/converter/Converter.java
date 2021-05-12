package com.lcn29.convert.converter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-10  17:52
 */
@FunctionalInterface
public interface Converter<S, T> {

	T convert(S source);
}
