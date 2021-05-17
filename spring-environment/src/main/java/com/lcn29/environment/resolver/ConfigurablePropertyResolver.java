package com.lcn29.environment.resolver;

import com.lcn29.environment.convert.support.ConfigurableConversionService;
import com.lcn29.environment.exception.MissingRequiredPropertiesException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-10  17:45
 */
public interface ConfigurablePropertyResolver extends PropertyResolver{

	ConfigurableConversionService getConversionService();

	void setConversionService(ConfigurableConversionService conversionService);

	void setPlaceholderPrefix(String placeholderPrefix);

	void setPlaceholderSuffix(String placeholderSuffix);

	void setValueSeparator(String valueSeparator);

	void setIgnoreUnresolvableNestedPlaceholders(boolean ignoreUnresolvableNestedPlaceholders);

	void setRequiredProperties(String... requiredProperties);

	void validateRequiredProperties() throws MissingRequiredPropertiesException;
}
