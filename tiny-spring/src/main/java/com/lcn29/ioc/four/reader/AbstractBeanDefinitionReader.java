package com.lcn29.ioc.four.reader;


import com.lcn29.ioc.four.bean.BeanDefinition;
import com.lcn29.ioc.four.loader.ResourceLoader;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 抽象 Bean 定义读取实现
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-22 09:28
 */
@Getter
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    /**
     * beanDefinition 容器
     */
    private Map<String, BeanDefinition> registry;

    /**
     * resource 加载器
     */
    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.registry = new HashMap<>();
        this.resourceLoader = resourceLoader;
    }
}
