package com.lcn29.spring.reader;

import com.lcn29.spring2.exception.BeanDefinitionStoreException;
import com.lcn29.spring2.xml.XmlReaderContext;
import org.w3c.dom.Document;

/**
 * <pre>
 * 从 Document 读取 BeanDefinition
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 20:29
 */
public interface BeanDefinitionDocumentReader {

    void registerBeanDefinitions(Document doc, XmlReaderContext readerContext)
            throws BeanDefinitionStoreException;
}
