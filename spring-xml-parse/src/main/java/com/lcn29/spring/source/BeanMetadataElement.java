package com.lcn29.spring.source;

/**
 * <pre>
 *  获取一个配置源对象
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 20:40
 */
public interface BeanMetadataElement {

    /**
     * 返回此元数据元素的可配置源 Object (可能为空)
     *
     * @return
     */
    Object getSource();
}
