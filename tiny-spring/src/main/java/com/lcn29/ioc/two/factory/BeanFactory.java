package com.lcn29.ioc.two.factory;

import com.lcn29.ioc.two.bean.BeanDefinition;

/**
 * <pre>
 *  Bean 容器接口定义
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 09:33
 */
public interface BeanFactory {


    /**
     * 向 Bean 容器里面注入 BeanDefinition
     * @param name 注册在容器里面的 key
     * @param beanDefinition
     */
    void registerBeanDefinition(String name, BeanDefinition beanDefinition);


    /**
     * 通过 name 从 Bean 容器中获取 BeanDefinition 中的 bean
     * @param name
     * @return
     */
    Object getBean(String name);




}
