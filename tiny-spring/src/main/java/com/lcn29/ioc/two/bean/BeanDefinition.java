package com.lcn29.ioc.two.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 *  Bean 定义
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-23 17:37
 */
@Getter
@Setter
@NoArgsConstructor
public class BeanDefinition {

    /**
     * 需要的 bean
     */
    private Object bean;

    /**
     * bean 自身的类型
     */
    private Class beanClass;

    /**
     * Class 的名字
     */
    private String beanClassName;

    /**
     * 设置 bean Class 和 beanClass Name
     * @param beanClassName
     */
    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try {
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
