package com.lcn29.ioc.four.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * bean 属性声明
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-21 22:29
 */
@Getter
@AllArgsConstructor
public class PropertyValue {

    private final String name;

    private final Object value;

}
