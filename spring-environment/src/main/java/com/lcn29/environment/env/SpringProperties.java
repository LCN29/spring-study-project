package com.lcn29.environment.env;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-13  15:19
 */
public class SpringProperties {

	private static final String PROPERTIES_RESOURCE_LOCATION = "spring.properties";

	private static final Properties localProperties = new Properties();

	static {
		try {
			ClassLoader cl = SpringProperties.class.getClassLoader();
			URL url = (cl != null ? cl.getResource(PROPERTIES_RESOURCE_LOCATION) : ClassLoader.getSystemResource(PROPERTIES_RESOURCE_LOCATION));
			if (url != null) {
				InputStream is = url.openStream();
				try {
					localProperties.load(is);
				} finally {
					is.close();
				}
			}
		} catch (IOException ex) {
		}
	}

	private SpringProperties() {
	}

	public static void setProperty(String key, String value) {
		if (value != null) {
			localProperties.setProperty(key, value);
		} else {
			localProperties.remove(key);
		}
	}

	public static String getProperty(String key) {
		String value = localProperties.getProperty(key);
		if (value == null) {
			try {
				value = System.getProperty(key);
			} catch (Throwable ex) {
			}
		}
		return value;
	}

	public static void setFlag(String key) {
		localProperties.put(key, Boolean.TRUE.toString());
	}

	public static boolean getFlag(String key) {
		return Boolean.parseBoolean(getProperty(key));
	}
}
