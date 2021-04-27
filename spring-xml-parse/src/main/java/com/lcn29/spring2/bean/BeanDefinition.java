package com.lcn29.spring2.bean;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 17:41
 */
public interface BeanDefinition extends BeanMetadataElement{

    String getParentName();

    String getBeanClassName();

    String getFactoryBeanName();
}
