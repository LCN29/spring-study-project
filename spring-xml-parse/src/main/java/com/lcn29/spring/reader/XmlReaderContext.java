package com.lcn29.spring.reader;

import com.lcn29.spring.registry.BeanDefinitionRegistry;
import com.lcn29.spring.resource.Resource;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-28 11:16
 */
public class XmlReaderContext extends ReaderContext {

    private final XmlBeanDefinitionReader reader;

    public XmlReaderContext(Resource resource, XmlBeanDefinitionReader reader) {
        super(resource);
        this.reader = reader;
    }

    public final BeanDefinitionRegistry getRegistry() {
        return this.reader.getRegistry();
    }

    public final ClassLoader getBeanClassLoader() {
        return this.reader.getBeanClassLoader();
    }
}
