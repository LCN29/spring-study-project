package com.lcn29.spring.reader;

import com.lcn29.spring.registry.BeanDefinitionRegistry;
import com.lcn29.spring.resource.loader.ResourceLoader;
import com.lcn29.spring.support.bean.BeanNameGenerator;
import com.lcn29.spring.support.bean.DefaultBeanNameGenerator;

/**
 * <pre>
 * 抽象的 beanDefinition 读取器实现
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 14:35
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    /**
     * 注册容器
     */
    private final BeanDefinitionRegistry registry;

    /**
     * bean class 加载器
     */
    private ClassLoader beanClassLoader;

    /**
     * 资源加载器
     */
    private ResourceLoader resourceLoader;

    /**
     * bean name 生成器
     */
    private BeanNameGenerator beanNameGenerator = new DefaultBeanNameGenerator();

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

    @Override
    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

    public BeanNameGenerator getBeanNameGenerator() {
        return this.beanNameGenerator;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
        this.beanNameGenerator = beanNameGenerator;
    }
}
