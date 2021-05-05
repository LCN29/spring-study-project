package com.lcn29.spring.bean.definition.method;

import com.lcn29.spring.source.BeanMetadataElement;
import lombok.Data;

/**
 * <pre>
 * 方法定义
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 17:20
 */
@Data
public class MethodOverride implements BeanMetadataElement {

    /**
     * 方法名
     */
    private final String methodName;

    /**
     * 是否重载
     */
    private boolean overloaded = true;

    /**
     * 元数据源
     */
    private Object source;

    protected MethodOverride(String methodName) {
        this.methodName = methodName;
    }

}
