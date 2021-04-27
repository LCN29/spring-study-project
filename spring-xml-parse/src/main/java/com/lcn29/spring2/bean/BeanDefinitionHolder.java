package com.lcn29.spring2.bean;

import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 17:43
 */
@Data
public class BeanDefinitionHolder implements BeanMetadataElement {

    private final BeanDefinition beanDefinition;

    private final String beanName;

    private final String[] aliases;

    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName) {
        this(beanDefinition, beanName, null);
    }

    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName, String[] aliases) {
        this.beanDefinition = beanDefinition;
        this.beanName = beanName;
        this.aliases = aliases;
    }

    @Override
    public Object getSource() {
        return this.beanDefinition.getSource();
    }
}
