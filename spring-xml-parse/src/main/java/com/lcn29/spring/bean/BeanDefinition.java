package com.lcn29.spring.bean;


import com.sun.istack.internal.Nullable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 20:40
 */
public interface BeanDefinition extends BeanMetadataElement {

    /**
     * 获取 bean 的 class 名
     *
     * @return
     */
    String getBeanClassName();

    /**
     * 返回这个 beanDefinition 的 FactoryBean 名, 如果有的话
     *
     * @return
     */
    String getFactoryBeanName();

    String getParentName();

    void setParentName(String parentName);
}
