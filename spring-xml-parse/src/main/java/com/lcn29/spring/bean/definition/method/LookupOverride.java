package com.lcn29.spring.bean.definition.method;

import java.lang.reflect.Method;

/**
 * <pre>
 * lookup 方法声明
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 17:22
 */
public class LookupOverride extends MethodOverride {

    private final String beanName;

    private Method method;

    public LookupOverride(String methodName, String beanName) {
        super(methodName);
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
