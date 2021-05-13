package com.lcn29.convert.support;

import com.lcn29.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:47
 */
final class StringToBooleanConverter implements Converter<String, Boolean> {

    private static final Set<String> trueValues = new HashSet<>(8);

    private static final Set<String> falseValues = new HashSet<>(8);

    static {
        trueValues.add("true");
        trueValues.add("on");
        trueValues.add("yes");
        trueValues.add("1");

        falseValues.add("false");
        falseValues.add("off");
        falseValues.add("no");
        falseValues.add("0");
    }

    @Override
    public Boolean convert(String source) {
        String value = source.trim();
        if (value.isEmpty()) {
            return null;
        }
        value = value.toLowerCase();
        if (trueValues.contains(value)) {
            return Boolean.TRUE;
        }
        else if (falseValues.contains(value)) {
            return Boolean.FALSE;
        }
        else {
            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
        }
    }

}
