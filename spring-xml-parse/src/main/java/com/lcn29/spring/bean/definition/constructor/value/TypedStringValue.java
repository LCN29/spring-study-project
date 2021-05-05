package com.lcn29.spring.bean.definition.constructor.value;

import com.lcn29.spring.source.BeanMetadataElement;
import lombok.Data;

/**
 * <pre>
 * 构造函数中的 value 参数封装
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 18:03
 */
@Data
public class TypedStringValue implements BeanMetadataElement {

    /**
     * value 值
     */
    private String value;

    /**
     * 目标参数类型
     */
    private volatile Object targetType;

    /**
     * 元数据源
     */
    private Object source;

    /**
     * 指定的类型名
     */
    private String specifiedTypeName;

    /**
     * 是否需要动态变动
     */
    private volatile boolean dynamic;

    public TypedStringValue(String value) {
        setValue(value);
    }

    public TypedStringValue(String value, Class<?> targetType) {
        setValue(value);
        setTargetType(targetType);
    }

    public TypedStringValue(String value, String targetTypeName) {
        setValue(value);
        setTargetTypeName(targetTypeName);
    }

    public void setTargetTypeName(String targetTypeName) {
        this.targetType = targetTypeName;
    }


}
