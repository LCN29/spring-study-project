package com.lcn29.spring.bean;

import com.lcn29.spring.reader.BeanMetadataAttributeAccessor;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 16:01
 */
public class AutowireCandidateQualifier extends BeanMetadataAttributeAccessor {

    public static final String VALUE_KEY = "value";

    private final String typeName;

    public AutowireCandidateQualifier(Class<?> type) {
        this(type.getName());
    }

    public AutowireCandidateQualifier(String typeName) {
        this.typeName = typeName;
    }

    public AutowireCandidateQualifier(Class<?> type, Object value) {
        this(type.getName(), value);
    }

    public AutowireCandidateQualifier(String typeName, Object value) {
        this.typeName = typeName;
        setAttribute(VALUE_KEY, value);
    }

    public String getTypeName() {
        return this.typeName;
    }
}
