package com.lcn29.environment.env;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-12  17:56
 */

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface PropertySources extends Iterable<PropertySource<?>> {

	default Stream<PropertySource<?>> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	boolean contains(String name);

	PropertySource<?> get(String name);
}
