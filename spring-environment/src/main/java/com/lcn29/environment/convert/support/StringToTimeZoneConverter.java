
package com.lcn29.environment.convert.support;


import com.lcn29.environment.convert.converter.Converter;
import com.lcn29.environment.util.StringUtils;

import java.util.TimeZone;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-12  18:21
 */
class StringToTimeZoneConverter implements Converter<String, TimeZone> {

	@Override
	public TimeZone convert(String source) {
		return StringUtils.parseTimeZoneString(source);
	}

}
