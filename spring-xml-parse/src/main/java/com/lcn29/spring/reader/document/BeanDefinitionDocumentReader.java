package com.lcn29.spring.reader.document;

import com.lcn29.spring.xml.context.XmlReaderContext;
import org.w3c.dom.Document;

/**
 * <pre>
 * 从 Document 读取 BeanDefinition
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 14:48
 */
public interface BeanDefinitionDocumentReader {


    /**
     * 开始 Document 的读取和 BeanDefinition 的注册
     *
     * @param doc
     * @param readerContext
     */
    void registerBeanDefinitions(Document doc, XmlReaderContext readerContext);

}
