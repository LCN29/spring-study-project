package com.lcn29.spring.bean.definition.constructor.list;

import com.lcn29.spring.bean.definition.Mergeable;
import com.lcn29.spring.source.BeanMetadataElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 可合并的 List
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 15:25
 */
@Data
public class ManagedList<E> extends ArrayList<E> implements Mergeable, BeanMetadataElement {

    /**
     * 元数据源
     */
    private Object source;

    /**
     * 元素类型名
     */
    private String elementTypeName;

    /**
     * 是否可合并
     */
    private boolean mergeEnabled;

    public ManagedList() {
    }

    public ManagedList(int initialCapacity) {
        super(initialCapacity);
    }


    @Override
    public List<E> merge(Object parent) {
        if (!this.mergeEnabled) {
            throw new IllegalStateException("Not allowed to merge when the 'mergeEnabled' property is set to 'false'");
        }

        if (parent == null) {
            return this;
        }
        if (!(parent instanceof List)) {
            throw new IllegalArgumentException("Cannot merge with object of type [" + parent.getClass() + "]");
        }

        List<E> merged = new ManagedList<>();
        merged.addAll((List<E>) parent);
        merged.addAll(this);
        return merged;
    }

}
