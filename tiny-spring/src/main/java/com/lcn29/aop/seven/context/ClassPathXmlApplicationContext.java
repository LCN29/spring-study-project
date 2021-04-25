package com.lcn29.aop.seven.context;


import com.lcn29.aop.seven.bean.BeanDefinition;
import com.lcn29.aop.seven.factory.AbstractBeanFactory;
import com.lcn29.aop.seven.factory.AutowireCapableBeanFactory;
import com.lcn29.aop.seven.loader.ResourceLoader;
import com.lcn29.aop.seven.reader.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * <pre>
 * classpath xml 上下文
 * </pre>
 *
 * @author canxin.li
 * @date 2021-04-22 16:12
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private String configLocation;

    public ClassPathXmlApplicationContext(String configLocation) throws Exception {
        this(configLocation, new AutowireCapableBeanFactory());
    }

    public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
        super(beanFactory);
        this.configLocation = configLocation;
        refresh();
    }

    @Override
    public void refresh() throws Exception {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
    }

}
