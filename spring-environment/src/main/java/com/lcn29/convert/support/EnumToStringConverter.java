package com.lcn29.convert.support;

import com.lcn29.convert.ConversionService;
import com.lcn29.convert.converter.Converter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:48
 */
final class EnumToStringConverter extends AbstractConditionalEnumConverter implements Converter<Enum<?>, String> {

    public EnumToStringConverter(ConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public String convert(Enum<?> source) {
        return source.name();
    }

}
