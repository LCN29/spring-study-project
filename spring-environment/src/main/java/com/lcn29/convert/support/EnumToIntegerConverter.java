package com.lcn29.convert.support;

import com.lcn29.convert.ConversionService;
import com.lcn29.convert.converter.Converter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:50
 */
final class EnumToIntegerConverter extends AbstractConditionalEnumConverter implements Converter<Enum<?>, Integer> {

    public EnumToIntegerConverter(ConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public Integer convert(Enum<?> source) {
        return source.ordinal();
    }

}

