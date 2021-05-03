package com.lcn29.spring.bean.definition.attribute;

import com.lcn29.spring.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 * 属性操作抽象类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 16:08
 */
public abstract class AttributeAccessorSupport implements AttributeAccessor {

    /**
     * 属性列表
     */
    private final Map<String, Object> attributes = new LinkedHashMap<>();

    @Override
    public void setAttribute(String name, Object value) {
        if (value != null) {
            this.attributes.put(name, value);
        } else {
            removeAttribute(name);
        }
    }

    @Override
    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    @Override
    public Object removeAttribute(String name) {
        return this.attributes.remove(name);
    }

    @Override
    public boolean hasAttribute(String name) {
        return this.attributes.containsKey(name);
    }

    @Override
    public String[] attributeNames() {
        return StringUtils.toStringArray(this.attributes.keySet());
    }
}
