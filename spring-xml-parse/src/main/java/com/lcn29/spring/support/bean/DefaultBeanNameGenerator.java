package com.lcn29.spring.support.bean;

import com.lcn29.spring.bean.definition.BeanDefinition;
import com.lcn29.spring.registry.BeanDefinitionRegistry;
import com.lcn29.spring.util.BeanDefinitionReaderUtils;

/**
 * <pre>
 * 默认的 BeanName 产生实现类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 14:10
 */
public class DefaultBeanNameGenerator implements BeanNameGenerator {


    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return BeanDefinitionReaderUtils.generateBeanName(definition, registry);
    }
}
