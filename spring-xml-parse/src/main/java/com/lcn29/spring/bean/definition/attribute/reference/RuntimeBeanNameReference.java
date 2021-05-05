package com.lcn29.spring.bean.definition.attribute.reference;

import lombok.Data;

/**
 * <pre>
 * 运行时 Bean 名引用
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 15:21
 */
@Data
public class RuntimeBeanNameReference implements BeanReference {

    private final String beanName;

    private Object source;

    public RuntimeBeanNameReference(String beanName) {
        this.beanName = beanName;
    }
}
