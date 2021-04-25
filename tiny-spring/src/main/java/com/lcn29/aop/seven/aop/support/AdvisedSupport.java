package com.lcn29.aop.seven.aop.support;

import lombok.Getter;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 18:15
 */
@Getter
@Setter
public class AdvisedSupport {

    /**
     * 被代理的类
     */
    private TargetSource targetSource;

    /**
     * 拦截器:  被代理的方法的, 内部封装了 aop 逻辑
     */
    private MethodInterceptor methodInterceptor;
}
