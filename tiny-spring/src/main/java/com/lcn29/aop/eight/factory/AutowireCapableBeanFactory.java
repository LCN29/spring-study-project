package com.lcn29.aop.eight.factory;


import com.lcn29.aop.eight.bean.BeanDefinition;
import com.lcn29.aop.eight.bean.BeanReference;
import com.lcn29.aop.eight.bean.PropertyValue;

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
	protected void applyPropertyValues(Object bean, BeanDefinition mbd) throws Exception {
		for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
			Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
			declaredField.setAccessible(true);
			Object value = propertyValue.getValue();
			if (value instanceof BeanReference) {
				BeanReference beanReference = (BeanReference) value;
				value = getBean(beanReference.getName());
			}
			declaredField.set(bean, value);
		}
	}
}
