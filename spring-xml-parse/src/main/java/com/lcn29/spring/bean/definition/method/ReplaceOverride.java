package com.lcn29.spring.bean.definition.method;

import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 * replace 方法声明
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-29 17:19
 */
public class ReplaceOverride extends MethodOverride {

    /**
     * 方法集合
     */
    private final String methodReplacerBeanName;

    private final List<String> typeIdentifiers = new LinkedList<>();

    public ReplaceOverride(String methodName, String methodReplacerBeanName) {
        super(methodName);
        this.methodReplacerBeanName = methodReplacerBeanName;
    }

    public void addTypeIdentifier(String identifier) {
        this.typeIdentifiers.add(identifier);
    }

}
