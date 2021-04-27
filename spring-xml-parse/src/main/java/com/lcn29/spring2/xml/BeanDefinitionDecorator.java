package com.lcn29.spring2.xml;

import com.lcn29.spring2.bean.BeanDefinitionHolder;
import com.sun.xml.internal.xsom.impl.parser.ParserContext;
import org.w3c.dom.Node;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 17:42
 */
public interface BeanDefinitionDecorator {

    BeanDefinitionHolder decorate(Node node, BeanDefinitionHolder definition, ParserContext parserContext);
}
