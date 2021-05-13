package com.lcn29.convert.support;

import com.lcn29.convert.converter.Converter;
import com.lcn29.convert.converter.ConverterFactory;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:50
 */
@SuppressWarnings({"rawtypes", "unchecked"})
final class IntegerToEnumConverterFactory implements ConverterFactory<Integer, Enum> {

    @Override
    public <T extends Enum> Converter<Integer, T> getConverter(Class<T> targetType) {
        return new IntegerToEnum(ConversionUtils.getEnumType(targetType));
    }


    private static class IntegerToEnum<T extends Enum> implements Converter<Integer, T> {

        private final Class<T> enumType;

        public IntegerToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(Integer source) {
            return this.enumType.getEnumConstants()[source];
        }
    }

}

