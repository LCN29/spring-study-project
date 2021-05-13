package com.lcn29.registry;


import com.lcn29.convert.converter.Converter;
import com.lcn29.convert.converter.ConverterFactory;
import com.lcn29.convert.converter.GenericConverter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-10  17:51
 */
public interface ConverterRegistry {

    void addConverter(Converter<?, ?> converter);

    <S, T> void addConverter(Class<S> sourceType, Class<T> targetType, Converter<? super S, ? extends T> converter);

    void addConverter(GenericConverter converter);

    void addConverterFactory(ConverterFactory<?, ?> factory);

    void removeConvertible(Class<?> sourceType, Class<?> targetType);
}
