package com.lcn29.spring.reader;

import com.lcn29.spring.reader.document.BeanDefinitionDocumentReader;
import com.lcn29.spring.reader.document.DefaultBeanDefinitionDocumentReader;
import com.lcn29.spring.registry.BeanDefinitionRegistry;
import com.lcn29.spring.resource.EncodedResource;
import com.lcn29.spring.resource.Resource;
import com.lcn29.spring.support.source.NullSourceExtractor;
import com.lcn29.spring.support.source.SourceExtractor;
import com.lcn29.spring.xml.DefaultNamespaceHandlerResolver;
import com.lcn29.spring.xml.NamespaceHandlerResolver;
import com.lcn29.spring.xml.context.XmlReaderContext;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 * XML bean Definition 读取器
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 14:31
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    /**
     * 元数据源和 Resource 绑定器
     */
    private SourceExtractor sourceExtractor = new NullSourceExtractor();

    /**
     * 命名空间解析器
     */
    private NamespaceHandlerResolver namespaceHandlerResolver;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 解析 Resource 的内容为 beanDefinition
     *
     * @param resource
     * @return
     * @throws Exception
     */
    public int loadBeanDefinitions(Resource resource) throws Exception {
        return loadBeanDefinitions(new EncodedResource(resource));
    }

    /**
     * 继续 EncodedResource 的内容为 BeanDefinition
     *
     * @param encodedResource
     * @return
     * @throws Exception
     */
    public int loadBeanDefinitions(EncodedResource encodedResource) throws Exception {

        // TODO 在 Spring 的实现中, 这里会将当前正在解析的 EncodeResource 放在当前线程的 ThreadLocal
        try {
            InputStream inputStream = encodedResource.getResource().getInputStream();
            try {
                InputSource inputSource = new InputSource(inputStream);
                if (encodedResource.getEncoding() != null) {
                    inputSource.setEncoding(encodedResource.getEncoding());
                }
                // 开始加载
                return doLoadBeanDefinitions(inputSource, encodedResource.getResource());
            } finally {
                inputStream.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException("IOException parsing XML document from " + encodedResource.getResource(), ex);
        }
    }

    /**
     * 开始加载 BeanDefinition
     *
     * @param inputSource
     * @param resource
     * @return
     */
    protected int doLoadBeanDefinitions(InputSource inputSource, Resource resource) {
        try {
            Document doc = doLoadDocument(inputSource);
            int count = registerBeanDefinitions(doc, resource);
            return count;
        } catch (Exception e) {
        }
        return 0;
    }


    public int registerBeanDefinitions(Document doc, Resource resource) {
        BeanDefinitionDocumentReader documentReader = new DefaultBeanDefinitionDocumentReader();
        int countBefore = getRegistry().getBeanDefinitionCount();
        documentReader.registerBeanDefinitions(doc, createReaderContext(resource));
        return getRegistry().getBeanDefinitionCount() - countBefore;
    }

    /**
     * 获取 Document
     * <p>
     * 在 Spring 中为了获取 Document 做了大量的封装, 格式校验等, 此处就直接按照 Sax 获取了
     * 在 Spring 中涉及到的类有 DocumentLoader, DefaultDocumentLoader, EntityResolver, ErrorHandler 等
     *
     * @param inputSource Sax 的输入源格式
     * @return
     * @throws Exception
     */
    private Document doLoadDocument(InputSource inputSource) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 不使用命名空间
        factory.setNamespaceAware(false);
        // 不启用校验
        factory.setValidating(false);
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputSource);
        return doc;
    }

    public XmlReaderContext createReaderContext(Resource resource) {
        return new XmlReaderContext(resource, sourceExtractor, this, getNamespaceHandlerResolver());
    }

    public NamespaceHandlerResolver getNamespaceHandlerResolver() {
        if (this.namespaceHandlerResolver == null) {
            this.namespaceHandlerResolver = createDefaultNamespaceHandlerResolver();
        }
        return this.namespaceHandlerResolver;
    }

    protected NamespaceHandlerResolver createDefaultNamespaceHandlerResolver() {
        ClassLoader cl = (getResourceLoader() != null ? getResourceLoader().getClassLoader() : getBeanClassLoader());
        return new DefaultNamespaceHandlerResolver(cl);
    }


}
