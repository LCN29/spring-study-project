package com.lcn29.spring.util;


import java.util.Collection;

/**
 * <pre>
 * 字符串工具类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 16:09
 */
public class StringUtils {

    /**
     * Collection 转为字符串数组
     *
     * @param collection 需要转换的列表
     * @return
     */
    public static String[] toStringArray(Collection<String> collection) {
        return (collection != null ? collection.toArray(new String[0]) : new String[0]);
    }
}
