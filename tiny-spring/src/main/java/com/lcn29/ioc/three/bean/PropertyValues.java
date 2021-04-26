package com.lcn29.ioc.three.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
 * @author lcn29
 * @date 2021-04-21 22:30
 */
@Getter
@NoArgsConstructor
public class PropertyValues {

    private final List<PropertyValue> propertyValues = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {
        // TODO 这里可以对于重复 propertyName 进行判断，直接用 list 没法做到
        this.propertyValues.add(pv);
    }

}
