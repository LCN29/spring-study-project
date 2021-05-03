package com.lcn29.spring.bean.definition.constructor;

import com.lcn29.spring.source.BeanMetadataElement;
import com.lcn29.spring.util.ClassUtils;
import com.sun.istack.internal.Nullable;
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
         * 输入源
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
