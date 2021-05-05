package com.lcn29.spring.util;

import org.w3c.dom.CharacterData;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * <pre>
 * XML Sax 解析工具类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 11:24
 */
public class DomUtils {

    /**
     * 通过标签名获取子元素的标签值
     *
     * @param ele          标签元素
     * @param childEleName 子标签元素的名字
     * @return
     */
    public static String getChildElementValueByTagName(Element ele, String childEleName) {
        Element child = getChildElementByTagName(ele, childEleName);
        return (child != null ? getTextValue(child) : null);
    }

    /**
     * 通过标签名获取子标签
     *
     * @param ele          标签元素
     * @param childEleName 标签名
     * @return
     */
    public static Element getChildElementByTagName(Element ele, String childEleName) {
        NodeList nl = ele.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element && nodeNameMatch(node, childEleName)) {
                return (Element) node;
            }
        }
        return null;
    }

    /**
     * 获取标签的值
     *
     * @param valueEle
     * @return
     */
    public static String getTextValue(Element valueEle) {
        StringBuilder sb = new StringBuilder();
        NodeList nl = valueEle.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node item = nl.item(i);
            if ((item instanceof CharacterData && !(item instanceof Comment)) || item instanceof EntityReference) {
                sb.append(item.getNodeValue());
            }
        }
        return sb.toString();
    }

    /**
     * 批量通过标签名获取子元素的标签列表
     *
     * @param ele
     * @param childEleNames
     * @return
     */
    public static List<Element> getChildElementsByTagName(Element ele, String... childEleNames) {
        List<String> childEleNameList = Arrays.asList(childEleNames);
        NodeList nl = ele.getChildNodes();
        List<Element> childEles = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element && nodeNameMatch(node, childEleNameList)) {
                childEles.add((Element) node);
            }
        }
        return childEles;
    }

    /**
     * 标签名是否和标签匹配
     *
     * @param node        标签
     * @param desiredName 标签名
     * @return
     */
    private static boolean nodeNameMatch(Node node, String desiredName) {
        return (desiredName.equals(node.getNodeName()) || desiredName.equals(node.getLocalName()));
    }

    /**
     * 标签是否在指定的标签名集合内
     *
     * @param node         标签
     * @param desiredNames 标签名集合
     * @return
     */
    private static boolean nodeNameMatch(Node node, Collection<?> desiredNames) {
        return (desiredNames.contains(node.getNodeName()) || desiredNames.contains(node.getLocalName()));
    }
}
