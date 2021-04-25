package com.lcn29.aop.seven;

import com.lcn29.aop.seven.aop.proxy.JdkDynamicAopProxy;
import com.lcn29.aop.seven.aop.support.AdvisedSupport;
import com.lcn29.aop.seven.aop.support.TargetSource;
import com.lcn29.aop.seven.context.ApplicationContext;
import com.lcn29.aop.seven.context.ClassPathXmlApplicationContext;
import com.lcn29.aop.seven.interceptor.MethodInvokeTimeInterceptor;
import com.lcn29.aop.seven.service.HelloWorldService;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 18:22
 */
public class SevenFnTest {

    @Test
    public void test() throws Exception {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("aop/seven/bean.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");

        // 1. 设置被代理对象 (JoinPoint)
        AdvisedSupport advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(HelloWorldService.class, helloWorldService);
        advisedSupport.setTargetSource(targetSource);

        // 2. 设置拦截器 (Advice)
        // 这里就是执行的 Aop 逻辑
        // 执行为: aop 的逻辑,  调用上面实现类的逻辑,  aop 的逻辑
        MethodInterceptor timerInterceptor = new MethodInvokeTimeInterceptor();
        advisedSupport.setMethodInterceptor(timerInterceptor);

        // 3. 创建代理 (Proxy)
        JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(advisedSupport);
        HelloWorldService helloWorldServiceProxy = (HelloWorldService) jdkDynamicAopProxy.getProxy();

        // 4. 基于 AOP 的调用
        helloWorldServiceProxy.helloWorld();
    }
}
