package com.lcn29.spring.reader.loader;

import com.lcn29.spring.reader.XmlValidationModeDetector;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * <pre>
 * Sax Document 解析接口默认实现类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-06 14:03
 */
public class DefaultDocumentLoader implements DocumentLoader {

    /**
     * 用于配置模式语言以进行验证
     */
    private static final String SCHEMA_LANGUAGE_ATTRIBUTE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

    /**
     * XSD 模式语言的 JAXP 标志
     */
    private static final String XSD_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";

    @Override
    public Document loadDocument(InputSource inputSource, EntityResolver entityResolver, ErrorHandler errorHandler, int validationMode, boolean namespaceAware) throws Exception {

        DocumentBuilderFactory factory = createDocumentBuilderFactory(validationMode, namespaceAware);
        DocumentBuilder builder = createDocumentBuilder(factory, entityResolver, errorHandler);
        return builder.parse(inputSource);
    }

    /**
     * 创建 DocumentBuilder 工厂
     *
     * @param validationMode
     * @param namespaceAware
     * @return
     * @throws ParserConfigurationException
     */
    protected DocumentBuilderFactory createDocumentBuilderFactory(int validationMode, boolean namespaceAware) throws ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(namespaceAware);

        // 只要不等于 0, 默认就是需要启动校验
        if (validationMode != XmlValidationModeDetector.VALIDATION_NONE) {

            factory.setValidating(true);
            if (validationMode == XmlValidationModeDetector.VALIDATION_XSD) {

                factory.setNamespaceAware(true);
                try {
                    factory.setAttribute(SCHEMA_LANGUAGE_ATTRIBUTE, XSD_SCHEMA_LANGUAGE);
                } catch (IllegalArgumentException ex) {

                    ParserConfigurationException pcex = new ParserConfigurationException("Unable to validate using XSD: Your JAXP provider [" + factory + "] does not support XML Schema. Are you running on Java 1.4 with Apache Crimson? " +
                            "Upgrade to Apache Xerces (or Java 1.5) for full XSD support.");

                    pcex.initCause(ex);
                    throw pcex;
                }

            }

        }
        return factory;
    }

    /**
     * 创建 DocumentBuilder
     *
     * @param factory
     * @param entityResolver
     * @param errorHandler
     * @return
     * @throws ParserConfigurationException
     */
    protected DocumentBuilder createDocumentBuilder(DocumentBuilderFactory factory, EntityResolver entityResolver, ErrorHandler errorHandler) throws ParserConfigurationException {

        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        if (entityResolver != null) {
            docBuilder.setEntityResolver(entityResolver);
        }
        if (errorHandler != null) {
            docBuilder.setErrorHandler(errorHandler);
        }
        return docBuilder;
    }
}
