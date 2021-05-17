package com.lcn29.environment.env;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-12  18:18
 */
public class PropertySourcesPropertyResolver extends AbstractPropertyResolver {

	private final PropertySources propertySources;

	public PropertySourcesPropertyResolver(PropertySources propertySources) {
		this.propertySources = propertySources;
	}

	@Override
	public boolean containsProperty(String key) {
		if (this.propertySources != null) {
			for (PropertySource<?> propertySource : this.propertySources) {
				if (propertySource.containsProperty(key)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getProperty(String key) {
		return getProperty(key, String.class, true);
	}

	@Override
	public <T> T getProperty(String key, Class<T> targetValueType) {
		return getProperty(key, targetValueType, true);
	}

	@Override
	protected String getPropertyAsRawString(String key) {
		return getProperty(key, String.class, false);
	}

	protected <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders) {
		if (this.propertySources != null) {
			for (PropertySource<?> propertySource : this.propertySources) {
				Object value = propertySource.getProperty(key);
				if (value != null) {
					if (resolveNestedPlaceholders && value instanceof String) {
						value = resolveNestedPlaceholders((String) value);
					}
					return convertValueIfNecessary(value, targetValueType);
				}
			}
		}
		return null;
	}
}
