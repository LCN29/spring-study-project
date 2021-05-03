package com.lcn29.spring.bean.definition.attribute;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * <pre>
 * 属性列表处理类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 16:26
 */
public interface PropertyValues extends Iterable<PropertyValue> {

    @Override
    default Iterator<PropertyValue> iterator() {
        return Arrays.asList(getPropertyValues()).iterator();
    }

    default Stream<PropertyValue> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    default Spliterator<PropertyValue> spliterator() {
        return Spliterators.spliterator(getPropertyValues(), 0);
    }

    /**
     * 获取所有的属性
     *
     * @return
     */
    PropertyValue[] getPropertyValues();

    /**
     * 获取指定的属性
     *
     * @param propertyName 属性名
     * @return
     */
    PropertyValue getPropertyValue(String propertyName);

    /**
     * 是否包含某个属性
     *
     * @param propertyName
     * @return
     */
    boolean contains(String propertyName);
}
