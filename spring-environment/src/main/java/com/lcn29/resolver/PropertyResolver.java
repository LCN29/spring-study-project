package com.lcn29.resolver;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-10  17:42
 */
public interface PropertyResolver {

	/**
	 * 是否包含某个属性
	 *
	 * @param key 属性 key
	 * @return 是否包含这个属性
	 */
	boolean containsProperty(String key);

	/**
	 * 获取某个属性，可能为空
	 */
	String getProperty(String key);

	/**
	 * 带默认值的属性获取
	 */
	String getProperty(String key, String defaultValue);

	/**
	 * 获取指定类型的某个属性,
	 */
	<T> T getProperty(String key, Class<T> targetType);

	/**
	 * 带默认值的获取指定类型属性
	 */
	<T> T getProperty(String key, Class<T> targetType, T defaultValue);

	/**
	 * 获取属性, 获取不到抛出异常
	 */
	String getRequiredProperty(String key) throws IllegalStateException;

	/**
	 * 获取属性，并转换为指定的类型，获取不到抛出异常
	 */
	<T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException;

	/**
	 * 将指定文本中 ${...} 替换成正常的属性, 如果没法获取对应的属性进行替换, 忽略
	 */
	String resolvePlaceholders(String text);

	/**
	 * 将指定文本中 ${...} 替换成正常的属性, 如果没法获取对应的属性进行替换, 抛出异常
	 */
	String resolveRequiredPlaceholders(String text) throws IllegalArgumentException;
}
