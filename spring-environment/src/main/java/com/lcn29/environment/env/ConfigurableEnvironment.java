package com.lcn29.environment.env;

import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-12  17:34
 */
public interface ConfigurableEnvironment extends Environment, ConfigurablePropertyResolver {

	void setActiveProfiles(String... profiles);

	void addActiveProfile(String profile);

	void setDefaultProfiles(String... profiles);

	MutablePropertySources getPropertySources();

	Map<String, Object> getSystemProperties();

	Map<String, Object> getSystemEnvironment();

	void merge(ConfigurableEnvironment parent);
}
