package com.lcn29.env;

import com.lcn29.convert.ConversionService;
import com.lcn29.convert.support.ConfigurableConversionService;
import com.lcn29.convert.support.DefaultConversionService;
import com.lcn29.exception.MissingRequiredPropertiesException;
import com.lcn29.util.ClassUtils;
import com.lcn29.util.PropertyPlaceholderHelper;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-12  18:18
 */
public abstract class AbstractPropertyResolver implements ConfigurablePropertyResolver {

	private volatile ConfigurableConversionService conversionService;

	private PropertyPlaceholderHelper nonStrictHelper;

	private PropertyPlaceholderHelper strictHelper;

	private boolean ignoreUnresolvableNestedPlaceholders = false;

	private String placeholderPrefix = "${";

	private String placeholderSuffix = "}";

	private String valueSeparator = ":";

	private final Set<String> requiredProperties = new LinkedHashSet<>();

	@Override
	public ConfigurableConversionService getConversionService() {
		ConfigurableConversionService cs = this.conversionService;
		if (cs == null) {
			synchronized (this) {
				cs = this.conversionService;
				if (cs == null) {
					cs = new DefaultConversionService();
					this.conversionService = cs;
				}
			}
		}
		return cs;
	}

	@Override
	public void setConversionService(ConfigurableConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@Override
	public void setPlaceholderPrefix(String placeholderPrefix) {
		this.placeholderPrefix = placeholderPrefix;
	}

	@Override
	public void setPlaceholderSuffix(String placeholderSuffix) {
		this.placeholderSuffix = placeholderSuffix;
	}

	@Override
	public void setValueSeparator(String valueSeparator) {
		this.valueSeparator = valueSeparator;
	}

	@Override
	public void setIgnoreUnresolvableNestedPlaceholders(boolean ignoreUnresolvableNestedPlaceholders) {
		this.ignoreUnresolvableNestedPlaceholders = ignoreUnresolvableNestedPlaceholders;
	}

	@Override
	public void setRequiredProperties(String... requiredProperties) {
		Collections.addAll(this.requiredProperties, requiredProperties);
	}

	@Override
	public void validateRequiredProperties() {
		MissingRequiredPropertiesException ex = new MissingRequiredPropertiesException();
		for (String key : this.requiredProperties) {
			if (this.getProperty(key) == null) {
				ex.addMissingRequiredProperty(key);
			}
		}
		if (!ex.getMissingRequiredProperties().isEmpty()) {
			throw ex;
		}
	}

	@Override
	public boolean containsProperty(String key) {
		return (getProperty(key) != null);
	}

	@Override
	public String getProperty(String key) {
		return getProperty(key, String.class);
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		return (value != null ? value : defaultValue);
	}

	@Override
	public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
		T value = getProperty(key, targetType);
		return (value != null ? value : defaultValue);
	}

	@Override
	public String getRequiredProperty(String key) throws IllegalStateException {
		String value = getProperty(key);
		if (value == null) {
			throw new IllegalStateException("Required key '" + key + "' not found");
		}
		return value;
	}

	@Override
	public <T> T getRequiredProperty(String key, Class<T> valueType) throws IllegalStateException {
		T value = getProperty(key, valueType);
		if (value == null) {
			throw new IllegalStateException("Required key '" + key + "' not found");
		}
		return value;
	}

	@Override
	public String resolvePlaceholders(String text) {
		if (this.nonStrictHelper == null) {
			this.nonStrictHelper = createPlaceholderHelper(true);
		}
		return doResolvePlaceholders(text, this.nonStrictHelper);
	}

	@Override
	public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
		if (this.strictHelper == null) {
			this.strictHelper = createPlaceholderHelper(false);
		}
		return doResolvePlaceholders(text, this.strictHelper);
	}

	protected String resolveNestedPlaceholders(String value) {
		if (value.isEmpty()) {
			return value;
		}
		return (this.ignoreUnresolvableNestedPlaceholders ?
				resolvePlaceholders(value) : resolveRequiredPlaceholders(value));
	}

	@SuppressWarnings("unchecked")
	protected <T> T convertValueIfNecessary(Object value, Class<T> targetType) {
		if (targetType == null) {
			return (T) value;
		}
		ConversionService conversionServiceToUse = this.conversionService;
		if (conversionServiceToUse == null) {
			// Avoid initialization of shared DefaultConversionService if
			// no standard type conversion is needed in the first place...
			if (ClassUtils.isAssignableValue(targetType, value)) {
				return (T) value;
			}
			conversionServiceToUse = DefaultConversionService.getSharedInstance();
		}
		return conversionServiceToUse.convert(value, targetType);
	}

	private PropertyPlaceholderHelper createPlaceholderHelper(boolean ignoreUnresolvablePlaceholders) {
		return new PropertyPlaceholderHelper(this.placeholderPrefix, this.placeholderSuffix, this.valueSeparator, ignoreUnresolvablePlaceholders);
	}

	private String doResolvePlaceholders(String text, PropertyPlaceholderHelper helper) {
		return helper.replacePlaceholders(text, this::getPropertyAsRawString);
	}

	protected abstract String getPropertyAsRawString(String key);
}
