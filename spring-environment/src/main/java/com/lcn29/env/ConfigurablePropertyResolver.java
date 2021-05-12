package com.lcn29.env;

import com.lcn29.convert.support.ConfigurableConversionService;
import com.lcn29.exception.MissingRequiredPropertiesException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-12  17:31
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
