package com.lcn29.env;

import com.lcn29.resolver.PropertyResolver;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-12  16:01
 */
public interface Environment extends PropertyResolver {

	String[] getActiveProfiles();

	String[] getDefaultProfiles();

	boolean acceptsProfiles(String... profiles);

	boolean acceptsProfiles(Profiles profiles);
}
