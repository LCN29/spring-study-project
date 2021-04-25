package com.lcn29.ioc.six.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 一个对象所有的 PropertyValue 列表
 *
 * 为什么封装而不是直接用List?因为可以封装一些操作。
 *
 * </pre>
 *
 * @author canxin.li
 * @date 2021-04-21 22:30
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();


    public PropertyValues() {
    }

    public void addPropertyValue(PropertyValue pv) {
        // TODO 这里可以对于重复 propertyName 进行判断，直接用 list 没法做到
        this.propertyValueList.add(pv);
    }

    public List<PropertyValue> getPropertyValues() {
        return this.propertyValueList;
    }

}
