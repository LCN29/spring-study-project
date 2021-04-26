package com.lcn29.aop.eight.aop.proxy;

import com.lcn29.aop.eight.aop.invocation.ReflectiveMethodInvocation;
import com.lcn29.aop.eight.aop.support.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <pre>
 * JDK 动态代理实现类
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-22 16:50
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

	private AdvisedSupport advised;

	public JdkDynamicAopProxy(AdvisedSupport advised) {
		this.advised = advised;
	}

    @Override
	public Object getProxy() {
		return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { advised.getTargetSource()
				.getTargetClass() }, this);
	}

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
		return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method,
				args));
	}

}
