package com.lcn29.sax;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * <pre>
 * Sax 方式解析 XML 文件
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 14:49
 */
public class XmlParseBySax {

    /**
     * xml 解析
     *
     * @param xmlFilePath xml 文件的路径
     * @throws Exception
     */
    public static void parseXml(String xmlFilePath) throws Exception {

        // 不启用校验
        boolean validating = false;
        // 不使用命名空间
        boolean namespaceAware = false;

        DocumentBuilderFactory factory = createDocumentBuilderFactory(validating, namespaceAware);
        DocumentBuilder docBuilder = createDocumentBuilder(factory, new MyEntityResolve(), new MyErrorHandler());
        Document doc = docBuilder.parse(new InputSource(xmlFilePath));

        // 根标签
        Element root = doc.getDocumentElement();
        // 子节点
        NodeList nodeList = root.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (!(node instanceof Element)) {
                continue;
            }

            Element ele = (Element) node;
            if (!"bean".equals(ele.getNodeName())) {
                continue;
            }
            String id = ele.getAttribute("id");
            String clazz = ele.getAttribute("class");
            String scope = ele.getAttribute("scope");

            System.out.println("Result: beanName: " + id + ", beanClass: " + clazz + ", scope: " + scope);
        }
    }


    /**
     * 创建 DocumentBuilder 工厂
     *
     * @param validating     是否需要对 xml 进行校验
     * @param namespaceAware 是否需要校验命名空间
     * @return
     */
    private static DocumentBuilderFactory createDocumentBuilderFactory(boolean validating, boolean namespaceAware) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(namespaceAware);
        factory.setValidating(validating);
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
    private static DocumentBuilder createDocumentBuilder(DocumentBuilderFactory factory, EntityResolver entityResolver, MyErrorHandler errorHandler) throws ParserConfigurationException {
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
