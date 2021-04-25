package com.lcn29.aop.seven.aop.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * 被代理的类
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 18:15
 */
@Getter
@AllArgsConstructor
public class TargetSource {

    /**
     * 被代理类的 class 类型
     */
    private Class targetClass;

    /**
     * 被代理的类
     */
    private Object target;
}
