package com.lcn29.spring.xml;

import com.lcn29.spring.bean.definition.BeanDefinition;
import com.lcn29.spring.bean.definition.holder.BeanDefinitionHolder;
import com.lcn29.spring.reader.document.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <pre>
 * 命名空间处理接口
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 14:14
 */
public interface NamespaceHandler {

    /**
     * 初始化
     */
    void init();


    /**
     * 解析标签
     *
     * @param element       标签
     * @param parserContext Resource 包装
     * @return
     */
    BeanDefinition parse(Element element, ParserContext parserContext);

    /**
     * 装饰 beanDefinitionHolder
     *
     * @param source        标签节点
     * @param definition    beanDefinition 的包装
     * @param parserContext Resource 包装
     * @return
     */
    BeanDefinitionHolder decorate(Node source, BeanDefinitionHolder definition, ParserContext parserContext);
}
