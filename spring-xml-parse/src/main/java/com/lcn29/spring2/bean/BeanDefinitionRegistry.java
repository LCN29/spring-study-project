package com.lcn29.spring2.bean;

import com.lcn29.spring2.exception.BeanDefinitionStoreException;

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
