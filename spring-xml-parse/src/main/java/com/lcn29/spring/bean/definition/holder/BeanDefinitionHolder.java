package com.lcn29.spring.bean.definition.holder;

import com.lcn29.spring.bean.definition.BeanDefinition;
import com.lcn29.spring.source.BeanMetadataElement;
import lombok.Data;

/**
 * <pre>
 * BeanDefinition 包装类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 11:34
 */
@Data
public class BeanDefinitionHolder implements BeanMetadataElement {

    /**
     * beanDefinition
     */
    private final BeanDefinition beanDefinition;

    /**
     * bean 的名字
     */
    private final String beanName;

    /**
     * 别名列表
     */
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
