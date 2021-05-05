package com.lcn29.spring.bean.definition.defaults;

import lombok.Data;

/**
 * <pre>
 * XMl 中配置的默认配置
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-28 17:11
 */
@Data
public class DocumentDefaultsDefinition implements DefaultsDefinition {

    private String lazyInit;

    private String merge;

    private String autowire;

    private String autowireCandidates;

    private String initMethod;

    private String destroyMethod;

    private Object source;

}
