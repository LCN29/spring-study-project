package com.lcn29.aop.eight.reader;


import com.lcn29.aop.eight.bean.BeanDefinition;
import com.lcn29.aop.eight.loader.ResourceLoader;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 抽象 Bean 定义读取实现
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-22 11:41
 */
@Getter
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private Map<String, BeanDefinition> registry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.registry = new HashMap<String, BeanDefinition>();
        this.resourceLoader = resourceLoader;
    }
}
