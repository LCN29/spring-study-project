package com.lcn29.sax;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * <pre>
 * Sax 方式解析 XML 文件
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 14:49
 */
public class XmlParseBySax {

    public static void parseXml(String xmlFilePath) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 不使用命名空间
        factory.setNamespaceAware(false);
        // 不启用校验
        factory.setValidating(false);
        MyHandler handler = new MyHandler();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        docBuilder.setEntityResolver(handler);
        docBuilder.setErrorHandler(handler);
        Document doc = docBuilder.parse(new InputSource(xmlFilePath));

        Element root = doc.getDocumentElement();
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

            System.out.println("Result: beanName: " + id + ", beanClass: "+ clazz +", scope: " + scope);
        }

    }

    /**
     * 自定义事件实现类
     */
    public static class MyHandler extends DefaultHandler {

        @Override
        public void characters(char ch[], int start, int length) throws SAXException {
            String s = new String(ch, start, length);
            System.out.println(s);
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attrs) {
            System.out.println(localName + "///" + qName + "///" + uri + "////" + attrs.getValue("id"));
        }
    }
}
