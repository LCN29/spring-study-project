package com.lcn29.aop.eight.aop.pointcut;

import com.lcn29.aop.eight.aop.advisor.Advisor;

/**
 * @author yihua.huang@dianping.com
 */
public interface PointcutAdvisor extends Advisor {

   Pointcut getPointcut();
}
