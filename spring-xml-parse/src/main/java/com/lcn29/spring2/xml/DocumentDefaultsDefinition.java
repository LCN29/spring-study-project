package com.lcn29.spring2.xml;

import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 17:53
 */
@Data
public class DocumentDefaultsDefinition  implements DefaultsDefinition{

    
    private String lazyInit;

    
    private String merge;

    
    private String autowire;

    
    private String autowireCandidates;

    
    private String initMethod;

    
    private String destroyMethod;

    
    private Object source;
}
