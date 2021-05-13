package com.lcn29.convert.support;

import com.lcn29.convert.converter.Converter;

import java.util.Currency;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:53
 */
class StringToCurrencyConverter implements Converter<String, Currency> {

    @Override
    public Currency convert(String source) {
        return Currency.getInstance(source);
    }

}
