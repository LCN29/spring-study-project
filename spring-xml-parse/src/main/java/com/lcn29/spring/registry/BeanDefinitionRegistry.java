package com.lcn29.spring.registry;

import com.lcn29.spring.bean.definition.BeanDefinition;

/**
 * <pre>
 * BeanDefinition 注册容器
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 11:33
 */
public interface BeanDefinitionRegistry extends AliasRegistry {

    /**
     * 向容器注册 BeanDefinition
     *
     * @param beanName       bean 的名字
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);


    /**
     * 当前的容器是否包含了这个名字的 beanDefinition
     *
     * @param beanName bean 的名字
     * @return 是否已经存在
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 当前容器里面已经有多少个 BeanDefinition
     *
     * @return
     */
    int getBeanDefinitionCount();

    /**
     * 某个 beanName 是否已经存在
     *
     * @param beanName bean 名
     * @return
     */
    boolean isBeanNameInUse(String beanName);
}
