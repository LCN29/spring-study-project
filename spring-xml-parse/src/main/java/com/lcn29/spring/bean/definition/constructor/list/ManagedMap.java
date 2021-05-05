package com.lcn29.spring.bean.definition.constructor.list;

import com.lcn29.spring.bean.definition.Mergeable;
import com.lcn29.spring.source.BeanMetadataElement;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 * 可合并的 Map
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 15:51
 */
@Data
public class ManagedMap<K, V> extends LinkedHashMap<K, V> implements Mergeable, BeanMetadataElement {

    /**
     * 元数据源
     */
    private Object source;

    /**
     * key 类型名
     */
    private String keyTypeName;

    /**
     * value 类型名
     */
    private String valueTypeName;

    /**
     * 是否可以合并
     */
    private boolean mergeEnabled;

    public ManagedMap() {
    }

    public ManagedMap(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public Object merge(Object parent) {
        if (!this.mergeEnabled) {
            throw new IllegalStateException("Not allowed to merge when the 'mergeEnabled' property is set to 'false'");
        }
        if (parent == null) {
            return this;
        }
        if (!(parent instanceof Map)) {
            throw new IllegalArgumentException("Cannot merge with object of type [" + parent.getClass() + "]");
        }
        Map<K, V> merged = new ManagedMap<>();
        merged.putAll((Map<K, V>) parent);
        merged.putAll(this);
        return merged;
    }
}
