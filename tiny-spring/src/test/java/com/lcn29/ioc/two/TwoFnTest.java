package com.lcn29.ioc.two;

import com.lcn29.ioc.two.bean.BeanDefinition;
import com.lcn29.ioc.two.factory.AutowireCapableBeanFactory;
import com.lcn29.ioc.two.factory.BeanFactory;
import com.lcn29.ioc.two.service.HelloWorldServiceImpl;
import org.junit.Test;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 14:05
 */
public class TwoFnTest {

    @Test
    public void test() {

        // 1.初始化 beanFactory
        BeanFactory beanFactory = new AutowireCapableBeanFactory();

        // 2.注入 bean
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName(HelloWorldServiceImpl.class.getName());
        beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

        // 3.获取bean
        HelloWorldServiceImpl helloWorldService = (HelloWorldServiceImpl) beanFactory.getBean("helloWorldService");
        helloWorldService.setAnotherWorld("Two .");

        helloWorldService.helloWorld();
    }
}
