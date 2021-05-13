package com.lcn29.convert.support;

import com.lcn29.convert.converter.Converter;
import com.lcn29.util.StringUtils;

import java.util.Locale;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:50
 */
final class StringToLocaleConverter implements Converter<String, Locale> {

    @Override
    public Locale convert(String source) {
        return StringUtils.parseLocale(source);
    }

}
