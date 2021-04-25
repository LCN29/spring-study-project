package com.lcn29.ioc.three.factory;


import com.lcn29.ioc.three.bean.BeanDefinition;
import com.lcn29.ioc.three.bean.PropertyValue;

import java.lang.reflect.Field;

/**
 * <pre>
 * 自动装配 Bean 容器
 * </pre>
 *
 * @author canxin.li
 * @date 2021-04-21 22:05
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        Object bean = createBeanInstance(beanDefinition);
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
        return beanDefinition.getBeanClass().newInstance();
    }

    protected void applyPropertyValues(Object bean, BeanDefinition mbd) throws Exception {
        for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
            Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
            declaredField.setAccessible(true);
            declaredField.set(bean, propertyValue.getValue());
        }
    }
}
