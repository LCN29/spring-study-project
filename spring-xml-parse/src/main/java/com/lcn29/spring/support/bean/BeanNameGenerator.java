package com.lcn29.spring.support.bean;

import com.lcn29.spring.bean.definition.BeanDefinition;
import com.lcn29.spring.registry.BeanDefinitionRegistry;

/**
 * <pre>
 * beanName 生成接口
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 14:05
 */
public interface BeanNameGenerator {

    /**
     * beanName 产生
     *
     * @param definition
     * @param registry
     * @return
     */
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);
}
