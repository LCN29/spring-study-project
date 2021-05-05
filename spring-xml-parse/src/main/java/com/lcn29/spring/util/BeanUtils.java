package com.lcn29.spring.util;

import java.lang.reflect.Constructor;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 10:24
 */
public class BeanUtils {

    /**
     * 获取指定 class 的实例
     *
     * @param clazz
     * @param assignableTo
     * @param <T>
     * @return
     */
    public static <T> T instantiateClass(Class<?> clazz, Class<T> assignableTo) {
        return (T) instantiateClass(clazz);
    }

    /**
     * 获取指定 class 的实例，在 Spring 的实现中这里借助了 Kotlin 的特性，这里简单的用反射
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T instantiateClass(Class<T> clazz) {

        if (clazz.isInterface()) {
            throw new RuntimeException("Specified class is an interface");
        }
        try {
            return instantiateClass(clazz.getDeclaredConstructor());
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }

    /**
     * 通过构造函数初始化实例， 在 Spring 的实现中这里借助了 Kotlin 的特性，这里简单的用反射
     *
     * @param ctor
     * @param args
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T instantiateClass(Constructor<T> ctor, Object... args) throws Exception {

        try {
            return ctor.newInstance(args);
        } catch (Exception ex) {
            throw new Exception("Is it an abstract class?", ex);
        }
    }
}
