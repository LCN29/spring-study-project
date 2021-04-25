package com.lcn29.ioc.four.reader;


import com.lcn29.ioc.four.bean.BeanDefinition;
import com.lcn29.ioc.four.loader.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 抽象 Bean 定义读取实现
 * </pre>
 *
 * @author canxin.li
 * @date 2021-04-22 09:28
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private Map<String, BeanDefinition> registry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.registry = new HashMap<>();
        this.resourceLoader = resourceLoader;
    }

    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

}
