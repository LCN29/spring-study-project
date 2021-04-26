package com.lcn29.ioc.five.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 对象 bean 引用的属性的声明定义
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-22 11:58
 */
@Getter
@Setter
public class BeanReference {

    private String name;

    private Object bean;

    public BeanReference(String name) {
        this.name = name;
    }
}
