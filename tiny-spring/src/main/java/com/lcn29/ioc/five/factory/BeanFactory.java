package com.lcn29.ioc.five.factory;

import com.lcn29.ioc.five.bean.BeanDefinition;

/**
 * <pre>
 * Bean 容器功能声明接口
 * </pre>
 *
 * @author canxin.li
 * @date 2021-04-21 22:03
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;

    void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception;
}
