package com.lcn29.environment.convert.support;

import com.lcn29.environment.convert.converter.Converter;
import com.lcn29.environment.util.StringUtils;

import java.util.UUID;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:54
 */
final class StringToUUIDConverter implements Converter<String, UUID> {

    @Override
    public UUID convert(String source) {
        return (StringUtils.hasLength(source) ? UUID.fromString(source.trim()) : null);
    }

}
