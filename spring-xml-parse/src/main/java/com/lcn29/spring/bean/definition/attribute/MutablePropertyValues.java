package com.lcn29.spring.bean.definition.attribute;

import com.lcn29.spring.bean.definition.Mergeable;

import java.util.*;
import java.util.stream.Stream;

/**
 * <pre>
 * 属性列表
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 16:29
 */
public class MutablePropertyValues implements PropertyValues {

    /**
     * 属性列表
     */
    private final List<PropertyValue> propertyValueList;

    /**
     * 属性名集合
     */
    private Set<String> processedProperties;


    public MutablePropertyValues() {
        this.propertyValueList = new ArrayList<>(0);
    }

    public MutablePropertyValues(PropertyValues original) {
        if (original != null) {
            PropertyValue[] pvs = original.getPropertyValues();
            this.propertyValueList = new ArrayList<>(pvs.length);
            for (PropertyValue pv : pvs) {
                this.propertyValueList.add(new PropertyValue(pv));
            }
        } else {
            this.propertyValueList = new ArrayList<>(0);
        }
    }

    @Override
    public Iterator<PropertyValue> iterator() {
        return Collections.unmodifiableList(this.propertyValueList).iterator();
    }

    @Override
    public Spliterator<PropertyValue> spliterator() {
        return Spliterators.spliterator(this.propertyValueList, 0);
    }

    @Override
    public Stream<PropertyValue> stream() {
        return this.propertyValueList.stream();
    }


    @Override
    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    @Override
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }

    @Override
    public boolean contains(String propertyName) {
        return (getPropertyValue(propertyName) != null || (this.processedProperties != null && this.processedProperties.contains(propertyName)));
    }

    /**
     * 添加属性
     *
     * @param pv 属性
     * @return
     */
    public MutablePropertyValues addPropertyValue(PropertyValue pv) {
        for (int i = 0; i < this.propertyValueList.size(); i++) {
            PropertyValue currentPv = this.propertyValueList.get(i);
            // 存在属性名一样的属性了
            if (currentPv.getName().equals(pv.getName())) {
                pv = mergeIfRequired(pv, currentPv);
                setPropertyValueAt(pv, i);
                return this;
            }
        }
        this.propertyValueList.add(pv);
        return this;
    }

    /**
     * 设置属性值
     *
     * @param pv 属性值
     * @param i  设置的位置
     */
    public void setPropertyValueAt(PropertyValue pv, int i) {
        this.propertyValueList.set(i, pv);
    }

    /**
     * 尝试进行属性合并
     *
     * @param newPv     新的属性值
     * @param currentPv 当前的属性值
     * @return
     */
    private PropertyValue mergeIfRequired(PropertyValue newPv, PropertyValue currentPv) {
        Object value = newPv.getValue();
        if (value instanceof Mergeable) {
            Mergeable mergeable = (Mergeable) value;
            if (mergeable.isMergeEnabled()) {
                Object merged = mergeable.merge(currentPv.getValue());
                return new PropertyValue(newPv.getName(), merged);
            }
        }
        return newPv;
    }
}
