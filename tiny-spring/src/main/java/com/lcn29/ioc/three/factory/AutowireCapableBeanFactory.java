package com.lcn29.ioc.three.factory;


import com.lcn29.ioc.three.bean.BeanDefinition;
import com.lcn29.ioc.three.bean.PropertyValue;

import java.lang.reflect.Field;

/**
 * <pre>
 * 自动装配 Bean 容器
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-21 22:05
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        Object bean = createBeanInstance(beanDefinition);
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    /**
     * 创建 bean 实例
     * @param beanDefinition bean 定义
     * @return
     * @throws Exception
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
        return beanDefinition.getBeanClass().newInstance();
    }

    /**
     * 向 bean 里面添加属性
     * @param bean bean 实例
     * @param mbd bean 定义
     * @throws Exception
     */
    protected void applyPropertyValues(Object bean, BeanDefinition mbd) throws Exception {
        for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
            Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
            declaredField.setAccessible(true);
            declaredField.set(bean, propertyValue.getValue());
        }
    }
}
