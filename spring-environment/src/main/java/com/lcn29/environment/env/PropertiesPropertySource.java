package com.lcn29.environment.env;

import java.util.Map;
import java.util.Properties;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-12  18:07
 */
public class PropertiesPropertySource extends MapPropertySource {

	@SuppressWarnings({"rawtypes", "unchecked"})
	public PropertiesPropertySource(String name, Properties source) {
		super(name, (Map) source);
	}

	protected PropertiesPropertySource(String name, Map<String, Object> source) {
		super(name, source);
	}

	@Override
	public String[] getPropertyNames() {
		synchronized (this.source) {
			return super.getPropertyNames();
		}
	}
}
