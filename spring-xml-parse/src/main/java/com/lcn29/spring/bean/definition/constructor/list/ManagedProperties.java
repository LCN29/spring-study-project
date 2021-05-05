package com.lcn29.spring.bean.definition.constructor.list;

import com.lcn29.spring.bean.definition.Mergeable;
import com.lcn29.spring.source.BeanMetadataElement;
import lombok.Data;

import java.util.Properties;

/**
 * <pre>
 * 可合并的 Properties
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 15:57
 */
@Data
public class ManagedProperties extends Properties implements Mergeable, BeanMetadataElement {


    /**
     * 元数据源
     */
    private Object source;

    /**
     * 是否可以合并
     */
    private boolean mergeEnabled;

    @Override
    public Object merge(Object parent) {
        if (!this.mergeEnabled) {
            throw new IllegalStateException("Not allowed to merge when the 'mergeEnabled' property is set to 'false'");
        }
        if (parent == null) {
            return this;
        }
        if (!(parent instanceof Properties)) {
            throw new IllegalArgumentException("Cannot merge with object of type [" + parent.getClass() + "]");
        }
        Properties merged = new ManagedProperties();
        merged.putAll((Properties) parent);
        merged.putAll(this);
        return merged;
    }
}
