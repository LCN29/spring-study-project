package com.lcn29.spring.bean.definition.attribute.reference;


import com.lcn29.spring.source.BeanMetadataElement;

/**
 * <pre>
 * bean 声明中的引用属性
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 10:23
 */
public interface BeanReference extends BeanMetadataElement {

    /**
     * 获取引用的 bean 名
     *
     * @return
     */
    String getBeanName();
}
