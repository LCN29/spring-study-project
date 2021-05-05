package com.lcn29.spring.reader;

import com.lcn29.spring.registry.BeanDefinitionRegistry;
import com.lcn29.spring.resource.loader.ResourceLoader;

/**
 * <pre>
 * BeanDefinition 读取器接口
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 14:31
 */
public interface BeanDefinitionReader {

    /**
     * 获取当前的 BeanDefinition 注册容器
     *
     * @return
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取 bean class 的加载器
     *
     * @return
     */
    ClassLoader getBeanClassLoader();

    /**
     * 获取资源加载器
     *
     * @return
     */
    ResourceLoader getResourceLoader();

}
