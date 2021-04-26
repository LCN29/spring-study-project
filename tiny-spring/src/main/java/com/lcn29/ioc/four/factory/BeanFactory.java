package com.lcn29.ioc.four.factory;

import com.lcn29.ioc.four.bean.BeanDefinition;

/**
 * <pre>
 * Bean 容器功能声明接口
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-21 22:03
 */
public interface BeanFactory {

    Object getBean(String name);

    void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception;
}
