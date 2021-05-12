package com.lcn29.env;

import java.util.function.Predicate;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-12  16:02
 */
@FunctionalInterface
public interface Profiles {

	boolean matches(Predicate<String> activeProfiles);

	static Profiles of(String... profiles) {
		return ProfilesParser.parse(profiles);
	}
}
