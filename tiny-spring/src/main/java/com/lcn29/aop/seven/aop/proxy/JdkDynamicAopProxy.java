package com.lcn29.aop.seven.aop.proxy;

import com.lcn29.aop.seven.aop.invocation.ReflectiveMethodInvocation;
import com.lcn29.aop.seven.aop.support.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <pre>
 * JDK 动态代理实现类
 * </pre>
 *
 * @author canxin.li
 * @date 2021-04-22 16:50
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    /**
     * 获取动态代理类
     * @return
     */
    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{advised.getTargetSource().getTargetClass()}, this);
    }

    /**
     * 调用动态代理类的代理方法
     * @param proxy 代理的类
     * @param method 代理的方法
     * @param args 代理的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

        MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
        return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(),
                method, args));
    }
}
