package com.lcn29.ioc.five;

import com.lcn29.ioc.five.bean.BeanDefinition;
import com.lcn29.ioc.five.factory.AbstractBeanFactory;
import com.lcn29.ioc.five.factory.AutowireCapableBeanFactory;
import com.lcn29.ioc.five.factory.BeanFactory;
import com.lcn29.ioc.five.loader.ResourceLoader;
import com.lcn29.ioc.five.service.AnotherServiceImpl;
import com.lcn29.ioc.five.reader.XmlBeanDefinitionReader;
import com.lcn29.ioc.five.service.HelloWorldServiceImpl;
import org.junit.Test;

import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 15:39
 */
public class FiveFnTest {

    /**
     * bean 延迟初始
     * @throws Exception
     */
    @Test
    public void testBeanLazyInit() throws Exception {

        // 1.读取配置
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions("ioc/five/bean.xml");

        // 2.初始化 BeanFactory 并注册 bean
        BeanFactory beanFactory = new AutowireCapableBeanFactory();

        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }

        // 3.获取bean
        HelloWorldServiceImpl helloWorldService = (HelloWorldServiceImpl) beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }

    /**
     * bean 立即初始
     * @throws Exception
     */
    @Test
    public void testBeanSingletonInit() throws Exception {

        // 1.读取配置
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions("ioc/five/bean.xml");

        // 2.初始化 BeanFactory 并注册 bean
        AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }

        // 3.初始化 bean
        beanFactory.preInstantiateSingletons();

        // 4.获取 bean
        HelloWorldServiceImpl helloWorldService = (HelloWorldServiceImpl) beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();


    }
}
