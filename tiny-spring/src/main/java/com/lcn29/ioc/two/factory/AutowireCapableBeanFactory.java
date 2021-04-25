package com.lcn29.ioc.two.factory;

import com.lcn29.ioc.two.bean.BeanDefinition;

/**
 * <pre>
 * 自动装配容器
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 11:46
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory{


    @Override
    protected Object doCreateBean(BeanDefinition beanDefinition) {
        try {
            Object bean = beanDefinition.getBeanClass().newInstance();
            return bean;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
