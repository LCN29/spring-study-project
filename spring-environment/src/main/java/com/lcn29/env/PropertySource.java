package com.lcn29.env;

import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-12  17:55
 */
@Data
public abstract class PropertySource<T> {

	protected final String name;

	protected final T source;

	public PropertySource(String name, T source) {
		this.name = name;
		this.source = source;
	}

	public PropertySource(String name) {
		this(name, (T) new Object());
	}

	public String getName() {
		return this.name;
	}

	public T getSource() {
		return this.source;
	}

	public boolean containsProperty(String name) {
		return (getProperty(name) != null);
	}


	public abstract Object getProperty(String name);

	public static PropertySource<?> named(String name) {
		return new ComparisonPropertySource(name);
	}

	public static class StubPropertySource extends PropertySource<Object> {

		public StubPropertySource(String name) {
			super(name, new Object());
		}

		@Override
		public String getProperty(String name) {
			return null;
		}
	}

	static class ComparisonPropertySource extends StubPropertySource {
		private static final String USAGE_ERROR =
				"ComparisonPropertySource instances are for use with collection comparison only";

		public ComparisonPropertySource(String name) {
			super(name);
		}

		@Override
		public Object getSource() {
			throw new UnsupportedOperationException(USAGE_ERROR);
		}

		@Override
		public boolean containsProperty(String name) {
			throw new UnsupportedOperationException(USAGE_ERROR);
		}

		@Override
		public String getProperty(String name) {
			throw new UnsupportedOperationException(USAGE_ERROR);
		}

	}

}
