package com.lcn29.spring.bean.definition.attribute;

import lombok.Data;

/**
 * <pre>
 * 属性的声明
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 16:21
 */

@Data
public class PropertyValue extends BeanMetadataAttributeAccessor {

    /**
     * 属性名
     */
    private final String name;

    /**
     * 属性值
     */
    private final Object value;

    /**
     * 是否需要转换
     */
    private boolean converted = false;

    /**
     * 转换后的值
     */
    private Object convertedValue;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public PropertyValue(PropertyValue original) {
        this.name = original.getName();
        this.value = original.getValue();
        this.converted = original.converted;
        this.convertedValue = original.convertedValue;
        setSource(original.getSource());
        copyAttributesFrom(original);
    }

    /**
     * 属性设置
     *
     * @param source
     */
    protected void copyAttributesFrom(AttributeAccessor source) {
        String[] attributeNames = source.attributeNames();
        for (String attributeName : attributeNames) {
            setAttribute(attributeName, source.getAttribute(attributeName));
        }
    }

}
