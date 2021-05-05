package com.lcn29.spring.bean.definition.constructor;

import com.lcn29.spring.bean.definition.Mergeable;
import com.lcn29.spring.source.BeanMetadataElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 构造函数封装类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 15:35
 */
public class ConstructorArgumentValues {

    /**
     * 带顺序的构造函数参数
     */
    private final Map<Integer, ValueHolder> indexedArgumentValues = new LinkedHashMap<>();

    /**
     * 不带顺序的构造函数参数
     */
    private final List<ValueHolder> genericArgumentValues = new ArrayList<>();

    /**
     * 构造函数参数列表是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return (this.indexedArgumentValues.isEmpty() && this.genericArgumentValues.isEmpty());
    }

    /**
     * 带顺序的构造函数参数是否包含这个位置的参数了
     *
     * @param index
     * @return
     */
    public boolean hasIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.containsKey(index);
    }

    /**
     * 添加新的构造函数参数
     *
     * @param index
     * @param value
     */
    public void addIndexedArgumentValue(int index, Object value) {
        addIndexedArgumentValue(index, new ValueHolder(value));
    }

    /**
     * 添加或者合并新的构造函数参数
     *
     * @param index
     * @param newValue
     */
    public void addIndexedArgumentValue(int index, ValueHolder newValue) {
        addOrMergeIndexedArgumentValue(index, newValue);
    }

    /**
     * 添加构造函数到无不带顺序的构造函数参数列表
     *
     * @param value
     */
    public void addGenericArgumentValue(Object value) {
        this.genericArgumentValues.add(new ValueHolder(value));
    }

    /**
     * 添加或者合并新的构造函数参数
     *
     * @param key
     * @param newValue
     */
    private void addOrMergeIndexedArgumentValue(Integer key, ValueHolder newValue) {
        ValueHolder currentValue = this.indexedArgumentValues.get(key);
        if (currentValue != null && newValue.getValue() instanceof Mergeable) {
            Mergeable mergeable = (Mergeable) newValue.getValue();
            if (mergeable.isMergeEnabled()) {
                newValue.setValue(mergeable.merge(currentValue.getValue()));
            }
        }
        this.indexedArgumentValues.put(key, newValue);
    }

    @Data
    public static class ValueHolder implements BeanMetadataElement {

        /**
         * 构造函数中的属性名
         */
        private String name;

        /**
         * 构造函数的属性名对应的值
         */
        private Object value;

        /**
         * 构造函数中的属性名的属行列席
         */
        private String type;

        /**
         * 这个属性是否需要进行转换, 例如 "2020-01-01" 转为 Date 之类的
         */
        private boolean converted = false;

        /**
         * 转换后的值
         */
        private Object convertedValue;

        /**
         * 元数据源
         */
        private Object source;

        public ValueHolder(Object value) {
            this.value = value;
        }

        public ValueHolder(Object value, String type) {
            this.value = value;
            this.type = type;
        }

        public ValueHolder(Object value, String type, String name) {
            this.value = value;
            this.type = type;
            this.name = name;
        }

    }
}
