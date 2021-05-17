package com.lcn29.environment.convert.support;


import com.lcn29.environment.convert.converter.Converter;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-12  18:21
 */
final class ZonedDateTimeToCalendarConverter implements Converter<ZonedDateTime, Calendar> {

    @Override
    public Calendar convert(ZonedDateTime source) {
        return GregorianCalendar.from(source);
    }

}
