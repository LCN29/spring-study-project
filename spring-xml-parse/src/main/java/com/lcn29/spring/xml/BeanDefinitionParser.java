package com.lcn29.spring.xml;

import com.lcn29.spring.bean.BeanDefinition;
import com.sun.xml.internal.xsom.impl.parser.ParserContext;
import org.w3c.dom.Element;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 17:41
 */
public interface BeanDefinitionParser {

    BeanDefinition parse(Element element, ParserContext parserContext);
}
