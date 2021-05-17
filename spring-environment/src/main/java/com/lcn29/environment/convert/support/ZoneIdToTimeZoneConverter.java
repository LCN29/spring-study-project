
package com.lcn29.environment.convert.support;

import com.lcn29.environment.convert.converter.Converter;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-12  18:21
 */
final class ZoneIdToTimeZoneConverter implements Converter<ZoneId, TimeZone> {

    @Override
    public TimeZone convert(ZoneId source) {
        return TimeZone.getTimeZone(source);
    }

}
