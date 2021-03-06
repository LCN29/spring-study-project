package com.lcn29.spring.support.state;

/**
 * <pre>
 * 表示解析 Bean 中的 Entry
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-29 11:05
 */
public class BeanEntry implements ParseState.Entry {

    private final String beanDefinitionName;

    public BeanEntry(String beanDefinitionName) {
        this.beanDefinitionName = beanDefinitionName;
    }
}
