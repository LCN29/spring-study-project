package com.lcn29.convert.support;

import com.lcn29.convert.converter.Converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:53
 */
final class PropertiesToStringConverter implements Converter<Properties, String> {

    @Override
    public String convert(Properties source) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream(256);
            source.store(os, null);
            return os.toString("ISO-8859-1");
        }
        catch (IOException ex) {
            // Should never happen.
            throw new IllegalArgumentException("Failed to store [" + source + "] into String", ex);
        }
    }

}
