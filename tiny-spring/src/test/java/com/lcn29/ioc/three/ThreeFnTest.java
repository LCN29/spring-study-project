package com.lcn29.ioc.three;

import com.lcn29.ioc.three.bean.BeanDefinition;
import com.lcn29.ioc.three.bean.PropertyValue;
import com.lcn29.ioc.three.bean.PropertyValues;
import com.lcn29.ioc.three.factory.AutowireCapableBeanFactory;
import com.lcn29.ioc.three.factory.BeanFactory;
import com.lcn29.ioc.three.service.HelloWorldServiceImpl;
import org.junit.Test;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 14:33
 */
public class ThreeFnTest {

    @Test
    public void test() throws Exception {

        // 1.初始化beanfactory
        BeanFactory beanFactory = new AutowireCapableBeanFactory();

        // 2.bean定义
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName(HelloWorldServiceImpl.class.getName());

        // 3.设置属性
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("anotherWorld", "Three ."));
        beanDefinition.setPropertyValues(propertyValues);

        // 4.生成bean
        beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

        // 5.获取bean
        HelloWorldServiceImpl helloWorldService = (HelloWorldServiceImpl) beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();

    }
}
