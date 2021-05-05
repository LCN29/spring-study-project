package com.lcn29.spring.support.state;


import com.lcn29.spring.util.StringUtils;

/**
 * <pre>
 * qualifier 标签解析状态
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 16:05
 */
public class QualifierEntry implements ParseState.Entry {

    private final String typeName;

    public QualifierEntry(String typeName) {
        if (!StringUtils.hasText(typeName)) {
            throw new IllegalArgumentException("Invalid qualifier type '" + typeName + "'");
        }
        this.typeName = typeName;
    }
}
