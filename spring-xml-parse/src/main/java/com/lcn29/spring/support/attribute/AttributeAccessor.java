package com.lcn29.spring.support.attribute;

/**
 * <pre>
 * 属性操作类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 16:05
 */
public interface AttributeAccessor {

    /**
     * 设置属性
     *
     * @param name  属性名
     * @param value 属性值
     */
    void setAttribute(String name, Object value);

    /**
     * 获取某个属性值
     *
     * @param name 属性名
     * @return
     */
    Object getAttribute(String name);

    /**
     * 移除某个属性
     *
     * @param name 需要移除的属性名
     * @return
     */
    Object removeAttribute(String name);

    /**
     * 是否有某个属性
     *
     * @param name 属性名
     * @return
     */
    boolean hasAttribute(String name);

    /**
     * 获取全部的属性名
     *
     * @return
     */
    String[] attributeNames();
}
