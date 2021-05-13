
package com.lcn29.convert.support;


import com.lcn29.convert.converter.Converter;
import com.lcn29.util.StringUtils;

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
