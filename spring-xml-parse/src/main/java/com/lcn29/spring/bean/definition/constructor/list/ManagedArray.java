package com.lcn29.spring.bean.definition.constructor.list;

import lombok.Data;

/**
 * <pre>
 * 可合并的数组
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 18:20
 */

@Data
public class ManagedArray extends ManagedList<Object> {

    volatile Class<?> resolvedElementType;

    public ManagedArray(String elementTypeName, int size) {
        super(size);
        setElementTypeName(elementTypeName);
    }
}
