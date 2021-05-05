package com.lcn29.spring.bean.definition.constructor.reference;

import com.lcn29.spring.bean.definition.attribute.reference.BeanReference;
import lombok.Data;

/**
 * <pre>
 * 构造函数中的 ref 封装
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 17:59
 */
@Data
public class RuntimeBeanReference implements BeanReference {

    private final String beanName;

    private final boolean toParent;

    private Object source;

    public RuntimeBeanReference(String beanName) {
        this(beanName, false);
    }

    public RuntimeBeanReference(String beanName, boolean toParent) {
        this.beanName = beanName;
        this.toParent = toParent;
    }
}
