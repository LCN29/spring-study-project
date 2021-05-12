package com.lcn29.env;

import com.lcn29.convert.support.ConfigurableConversionService;

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

	public static final String VALUE_SEPARATOR = ":";

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
}
