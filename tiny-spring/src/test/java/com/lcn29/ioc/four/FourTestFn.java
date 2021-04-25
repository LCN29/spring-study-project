package com.lcn29.ioc.four;

import com.lcn29.ioc.four.factory.AutowireCapableBeanFactory;
import com.lcn29.ioc.four.factory.BeanFactory;
import com.lcn29.ioc.four.loader.ResourceLoader;
import com.lcn29.ioc.four.reader.XmlBeanDefinitionReader;
import com.lcn29.ioc.four.bean.BeanDefinition;
import com.lcn29.ioc.four.service.HelloWorldServiceImpl;
import org.junit.Test;

import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 14:52
 */
public class FourTestFn {

    @Test
    public void test() throws Exception {

        // 1.读取配置
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions("ioc/four/four/bean.xml");

        // 2.初始化 BeanFactory 并注册 bean
        BeanFactory beanFactory = new AutowireCapableBeanFactory();
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }

        // 3.获取bean
        HelloWorldServiceImpl helloWorldService = (HelloWorldServiceImpl) beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();

    }
}
