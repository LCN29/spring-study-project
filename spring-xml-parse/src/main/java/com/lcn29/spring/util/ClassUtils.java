package com.lcn29.spring.util;

import com.sun.istack.internal.Nullable;

/**
 * <pre>
 * Class 工具类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 15:49
 */
public class ClassUtils {

    /**
     * 类名和指定的类是否匹配
     *
     * @param clazz    指定的类
     * @param typeName 类型字符串
     * @return 是否匹配
     */
    public static boolean matchesTypeName(Class<?> clazz, String typeName) {
        return (typeName != null && (typeName.equals(clazz.getTypeName()) || typeName.equals(clazz.getSimpleName())));
    }
}
