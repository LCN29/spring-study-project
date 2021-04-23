package com.lcn29.ioc.one.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  Bean 定义
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-23 17:37
 */
@Getter
@AllArgsConstructor
public class BeanDefinition {

    /**
     * 需要的 bean
     */
    private Object bean;

}
