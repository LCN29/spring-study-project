package com.lcn29.environment.env;

import com.lcn29.environment.util.StringUtils;

import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-12  18:08
 */
public class MapPropertySource extends EnumerablePropertySource<Map<String, Object>> {

	public MapPropertySource(String name, Map<String, Object> source) {
		super(name, source);
	}

	@Override
	public Object getProperty(String name) {
		return this.source.get(name);
	}

	@Override
	public boolean containsProperty(String name) {
		return this.source.containsKey(name);
	}

	@Override
	public String[] getPropertyNames() {
		return StringUtils.toStringArray(this.source.keySet());
	}
}
