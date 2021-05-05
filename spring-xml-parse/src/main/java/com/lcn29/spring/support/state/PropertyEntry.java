package com.lcn29.spring.support.state;


import com.lcn29.spring.util.StringUtils;

/**
 * <pre>
 * 表示解析属性中的 Entry
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 13:53
 */
public class PropertyEntry implements ParseState.Entry {

    private final String name;

    public PropertyEntry(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Invalid property name '" + name + "'");
        }

        this.name = name;
    }
}
