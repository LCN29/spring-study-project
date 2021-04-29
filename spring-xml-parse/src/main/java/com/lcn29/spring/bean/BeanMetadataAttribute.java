package com.lcn29.spring.bean;

import com.sun.istack.internal.Nullable;
import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-29 14:19
 */
@Data
public class BeanMetadataAttribute implements BeanMetadataElement{

    private final String name;

    private final Object value;

    private Object source;

    public BeanMetadataAttribute(String name, Object value) {
        this.name = name;
        this.value = value;
    }

}
