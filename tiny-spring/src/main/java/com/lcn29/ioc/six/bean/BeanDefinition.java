package com.lcn29.ioc.six.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Bean 声明定义
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-21 22:28
 */

@Getter
@Setter
public class BeanDefinition {

    private Object bean;

    private Class beanClass;

    private String beanClassName;

    private PropertyValues propertyValues = new PropertyValues();

    public BeanDefinition() {
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try {
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
