package com.lcn29.spring.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * <pre>
 *  Collection 集合工具类
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 19:04
 */
public class CollectionUtils {

    /**
     * 从集合中找到第一个匹配的对象
     *
     * @param source
     * @param candidates
     * @param <E>
     * @return
     */
    public static <E> E findFirstMatch(Collection<?> source, Collection<E> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) {
            return null;
        }
        for (Object candidate : candidates) {
            if (source.contains(candidate)) {
                return (E) candidate;
            }
        }
        return null;
    }

    /**
     * 判断集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * 把 Properties 的属性合并到指定的 Map 对象中
     *
     * @param props
     * @param map
     * @param <K>
     * @param <V>
     */
    public static <K, V> void mergePropertiesIntoMap(Properties props, Map<K, V> map) {
        if (props != null) {
            for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements(); ) {
                String key = (String) en.nextElement();
                Object value = props.get(key);
                if (value == null) {
                    value = props.getProperty(key);
                }
                map.put((K) key, (V) value);
            }
        }
    }
}
