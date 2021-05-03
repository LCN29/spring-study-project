package com.lcn29.spring.bean.definition.attribute;

import com.lcn29.spring.source.BeanMetadataElement;
import lombok.Data;

/**
 * <pre>
 * 一个 key-value 风格的属性类, bean definition 的一部分。
 * 除了 key-value 的值外，同时保持 source
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 16:16
 */
@Data
public class BeanMetadataAttribute implements BeanMetadataElement {

    private final String name;

    private final Object value;

    private Object source;

    public BeanMetadataAttribute(String name, Object value) {
        this.name = name;
        this.value = value;
    }

}
