package com.lcn29.spring.util;

import com.lcn29.spring.exception.BeanInstantiationException;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 17:11
 */
public class BeanUtils {

    public static <T> T instantiateClass(Class<?> clazz, Class<T> assignableTo)  {
        return (T) instantiateClass(clazz);
    }

    public static <T> T instantiateClass(Class<T> clazz){

        if (clazz.isInterface()) {
            throw new RuntimeException("Specified class is an interface");
        }
        try {
            return instantiateClass(clazz.getDeclaredConstructor());
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }

    public static <T> T instantiateClass(Constructor<T> ctor, Object... args) throws Exception {

        try {
            return ctor.newInstance(args);
        } catch (Exception ex) {
            throw new Exception("Is it an abstract class?", ex);
        }
    }

}
