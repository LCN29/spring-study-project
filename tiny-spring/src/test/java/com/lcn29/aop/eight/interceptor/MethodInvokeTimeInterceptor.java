package com.lcn29.aop.eight.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 18:23
 */
public class MethodInvokeTimeInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        long time = System.nanoTime();

        System.out.println("被代理的方法名: " + methodInvocation.getMethod().getName());

        // 执行被代理的方法
        Object proceed = methodInvocation.proceed();

        System.out.println("被代理的方法名: " + methodInvocation.getMethod().getName() + ", 方法调用消耗时间:" + (System.nanoTime() - time));

        return proceed;
    }
}
