package com.lcn29.convert.support;

import com.lcn29.convert.converter.Converter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:46
 */
final class NumberToCharacterConverter implements Converter<Number, Character> {

    @Override
    public Character convert(Number source) {
        return (char) source.shortValue();
    }

}