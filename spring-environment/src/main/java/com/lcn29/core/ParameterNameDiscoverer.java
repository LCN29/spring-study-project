package com.lcn29.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 21:33
 */
public interface ParameterNameDiscoverer {

    String[] getParameterNames(Method method);

    String[] getParameterNames(Constructor<?> ctor);
}
