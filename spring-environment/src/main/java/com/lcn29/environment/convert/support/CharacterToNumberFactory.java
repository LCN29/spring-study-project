package com.lcn29.environment.convert.support;

import com.lcn29.environment.convert.converter.Converter;
import com.lcn29.environment.convert.converter.ConverterFactory;
import com.lcn29.environment.util.NumberUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:47
 */
final class CharacterToNumberFactory implements ConverterFactory<Character, Number> {

    @Override
    public <T extends Number> Converter<Character, T> getConverter(Class<T> targetType) {
        return new CharacterToNumber<>(targetType);
    }

    private static final class CharacterToNumber<T extends Number> implements Converter<Character, T> {

        private final Class<T> targetType;

        public CharacterToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(Character source) {
            return NumberUtils.convertNumberToTargetClass((short) source.charValue(), this.targetType);
        }
    }

}

