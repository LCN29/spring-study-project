package com.lcn29.ioc.one;

import com.lcn29.ioc.one.bean.BeanDefinition;
import com.lcn29.ioc.one.factory.BeanFactory;
import org.junit.Test;
import com.lcn29.ioc.one.service.HelloWorldServiceImpl;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-23 17:56
 */
public class OneFnTest {

    @Test
    public void testFn() {

        BeanFactory beanFactory = new BeanFactory();

        BeanDefinition beanDefinition = new BeanDefinition(new HelloWorldServiceImpl());

        // 注册 beanDefinition
        beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

        // 获取 bean
        HelloWorldServiceImpl helloWorldService = (HelloWorldServiceImpl) beanFactory.getBean("helloWorldService");

        helloWorldService.setAnotherWorld("One .");

        helloWorldService.helloWorld();

    }
}
