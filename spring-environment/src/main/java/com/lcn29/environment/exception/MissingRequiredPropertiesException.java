package com.lcn29.environment.exception;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-10  17:49
 */
public class MissingRequiredPropertiesException extends IllegalStateException {

	private final Set<String> missingRequiredProperties = new LinkedHashSet<>();

	public void addMissingRequiredProperty(String key) {
		this.missingRequiredProperties.add(key);
	}

	@Override
	public String getMessage() {
		return "The following properties were declared as required but could not be resolved: " +  getMissingRequiredProperties();
	}

	public Set<String> getMissingRequiredProperties() {
		return this.missingRequiredProperties;
	}
}
