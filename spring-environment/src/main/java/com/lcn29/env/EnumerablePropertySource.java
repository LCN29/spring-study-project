package com.lcn29.env;

import com.lcn29.util.ObjectUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-12  18:09
 */
public abstract class EnumerablePropertySource<T> extends PropertySource<T> {

	public EnumerablePropertySource(String name, T source) {
		super(name, source);
	}

	protected EnumerablePropertySource(String name) {
		super(name);
	}

	@Override
	public boolean containsProperty(String name) {
		return ObjectUtils.containsElement(getPropertyNames(), name);
	}

	public abstract String[] getPropertyNames();
}
