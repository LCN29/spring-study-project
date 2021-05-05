package com.lcn29.spring.xml.context;

import com.lcn29.spring.bean.definition.BeanDefinition;
import com.lcn29.spring.reader.XmlBeanDefinitionReader;
import com.lcn29.spring.registry.BeanDefinitionRegistry;
import com.lcn29.spring.resource.Resource;
import com.lcn29.spring.support.source.SourceExtractor;
import com.lcn29.spring.xml.NamespaceHandlerResolver;

/**
 * <pre>
 * xml 类型的  Resource 包装
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 14:49
 */
public class XmlReaderContext extends ReaderContext {

    /**
     * xml Reader 的引用
     */
    private final XmlBeanDefinitionReader reader;

    /**
     * 命名空间解析器引用
     */
    private final NamespaceHandlerResolver namespaceHandlerResolver;

    public XmlReaderContext(Resource resource, SourceExtractor sourceExtractor, XmlBeanDefinitionReader reader, NamespaceHandlerResolver namespaceHandlerResolver) {
        super(resource, sourceExtractor);
        this.reader = reader;
        this.namespaceHandlerResolver = namespaceHandlerResolver;
    }

    /**
     * 获取注册容器
     *
     * @return
     */
    public final BeanDefinitionRegistry getRegistry() {
        return this.reader.getRegistry();
    }

    /**
     * 获取 bean 加载器
     *
     * @return
     */
    public final ClassLoader getBeanClassLoader() {
        return this.reader.getBeanClassLoader();
    }

    /**
     * 获取 bean name
     *
     * @param beanDefinition
     * @return
     */
    public String generateBeanName(BeanDefinition beanDefinition) {
        return this.reader.getBeanNameGenerator().generateBeanName(beanDefinition, getRegistry());
    }

    /**
     * 获取命名空间解析器
     *
     * @return
     */
    public final NamespaceHandlerResolver getNamespaceHandlerResolver() {
        return this.namespaceHandlerResolver;
    }


}
