package com.lcn29.spring.exception;

import com.sun.istack.internal.Nullable;

/**
 * <pre>
 * BeanDefinition 注册失败
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 20:29
 */
public class BeanDefinitionStoreException extends RuntimeException {

    public BeanDefinitionStoreException() {
    }

    public BeanDefinitionStoreException(String msg) {
        super(msg);
    }

    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
