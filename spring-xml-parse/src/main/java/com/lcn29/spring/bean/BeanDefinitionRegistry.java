package com.lcn29.spring.bean;

import com.lcn29.spring.exception.BeanDefinitionStoreException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 19:06
 */
public interface BeanDefinitionRegistry extends AliasRegistry{

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
            throws BeanDefinitionStoreException;

    boolean containsBeanDefinition(String beanName);

}
