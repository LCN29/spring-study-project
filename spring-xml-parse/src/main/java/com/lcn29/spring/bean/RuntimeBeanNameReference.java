package com.lcn29.spring.bean;

import com.sun.istack.internal.Nullable;
import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 15:21
 */
@Data
public class RuntimeBeanNameReference implements BeanReference {

    private final String beanName;

    @Nullable
    private Object source;

    public RuntimeBeanNameReference(String beanName) {
        this.beanName = beanName;
    }
}
