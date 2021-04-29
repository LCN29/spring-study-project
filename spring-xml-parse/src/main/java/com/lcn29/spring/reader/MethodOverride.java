package com.lcn29.spring.reader;

import com.lcn29.spring.bean.BeanMetadataElement;
import com.sun.istack.internal.Nullable;
import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-29 17:04
 */

@Data
public class MethodOverride implements BeanMetadataElement {

    private final String methodName;

    private boolean overloaded = true;

    @Nullable
    private Object source;

    protected MethodOverride(String methodName) {
        this.methodName = methodName;
    }
}
