package com.lcn29.environment.convert.support;

import com.lcn29.environment.convert.converter.Converter;

import java.nio.charset.Charset;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:52
 */
class StringToCharsetConverter implements Converter<String, Charset> {

    @Override
    public Charset convert(String source) {
        return Charset.forName(source);
    }

}
