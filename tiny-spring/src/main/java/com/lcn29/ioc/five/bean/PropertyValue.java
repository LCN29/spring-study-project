package com.lcn29.ioc.five.bean;

/**
 * <pre>
 * bean 属性声明
 * </pre>
 *
 * @author canxin.li
 * @date 2021-04-21 22:29
 */
public class PropertyValue {

    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

}
