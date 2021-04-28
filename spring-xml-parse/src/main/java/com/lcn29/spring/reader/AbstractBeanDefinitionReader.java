package com.lcn29.spring.reader;

import com.lcn29.spring.registry.BeanDefinitionRegistry;
import com.sun.istack.internal.Nullable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 20:28
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ClassLoader beanClassLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public final BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }


}
