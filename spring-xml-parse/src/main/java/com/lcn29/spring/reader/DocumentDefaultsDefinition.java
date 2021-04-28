package com.lcn29.spring.reader;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-28 17:11
 */
public class DocumentDefaultsDefinition implements DefaultsDefinition{

    private Object source;

    @Override
    public Object getSource() {
        return this.source;
    }
}
