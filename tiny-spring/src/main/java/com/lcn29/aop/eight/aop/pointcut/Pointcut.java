package com.lcn29.aop.eight.aop.pointcut;

import com.lcn29.aop.eight.aop.filter.ClassFilter;
import com.lcn29.aop.eight.aop.filter.MethodMatcher;

/**
 * @author yihua.huang@dianping.com
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

}
