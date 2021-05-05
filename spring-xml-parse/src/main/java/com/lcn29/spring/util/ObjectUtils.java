package com.lcn29.spring.util;

/**
 * <pre>
 * 对象工具类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 10:52
 */
public class ObjectUtils {

    /**
     * 获取对象 16 进制表示的 hashCode
     *
     * @param obj
     * @return
     */
    public static String getIdentityHexString(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }
}
