package com.lcn29.convert.support;

import com.lcn29.convert.TypeDescriptor;
import com.lcn29.convert.converter.ConditionalConverter;
import com.lcn29.convert.converter.Converter;
import com.lcn29.convert.converter.ConverterFactory;
import com.lcn29.util.NumberUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:42
 */
public class NumberToNumberConverterFactory implements ConverterFactory<Number, Number>, ConditionalConverter {

    @Override
    public <T extends Number> Converter<Number, T> getConverter(Class<T> targetType) {
        return new NumberToNumber<>(targetType);
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return !sourceType.equals(targetType);
    }


    private static final class NumberToNumber<T extends Number> implements Converter<Number, T> {

        private final Class<T> targetType;

        public NumberToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(Number source) {
            return NumberUtils.convertNumberToTargetClass(source, this.targetType);
        }
    }
}
