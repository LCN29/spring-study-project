package com.lcn29.aop.eight.aop.filter;

import java.lang.reflect.Method;

/**
 * @author yihua.huang@dianping.com
 */
public interface MethodMatcher {

    boolean matches(Method method, Class targetClass);
}
