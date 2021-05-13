package com.lcn29.aop.eight.aop.filter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-01  17:35
 */
public interface ClassFilter {

    boolean matches(Class targetClass);
}
