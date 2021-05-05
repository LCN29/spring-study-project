package com.lcn29.spring.bean.definition;

/**
 * <pre>
 * 抽象 BeanDefinition 实现
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 13:56
 */
public class GenericBeanDefinition extends AbstractBeanDefinition {

    private String parentName;

    public GenericBeanDefinition() {
        super();
    }

    @Override
    public String getParentName() {
        return this.parentName;
    }

    @Override
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
