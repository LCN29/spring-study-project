package com.lcn29.spring.bean;

import com.lcn29.spring.registry.BeanDefinitionRegistry;
import com.lcn29.spring.util.BeanDefinitionReaderUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 16:11
 */
public class DefaultBeanNameGenerator implements BeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return BeanDefinitionReaderUtils.generateBeanName(definition, registry);
    }
}
