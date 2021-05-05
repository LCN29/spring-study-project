package com.lcn29.spring.reader.document;

import com.lcn29.spring.bean.definition.BeanDefinition;
import com.lcn29.spring.xml.context.XmlReaderContext;
import lombok.Data;

/**
 * <pre>
 * XML 标签解析中的内容包装
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 16:34
 */
@Data
public class ParserContext {

    /**
     * 当前的 Resource 包装
     */
    private final XmlReaderContext readerContext;

    /**
     * 正在调用的 BeanDefinition 解析器
     */
    private final BeanDefinitionParserDelegate delegate;

    /**
     * 正在解析的 BeanDefinition
     */
    private BeanDefinition containingBeanDefinition;

    public ParserContext(XmlReaderContext readerContext, BeanDefinitionParserDelegate delegate) {
        this.readerContext = readerContext;
        this.delegate = delegate;
    }

    public ParserContext(XmlReaderContext readerContext, BeanDefinitionParserDelegate delegate, BeanDefinition containingBeanDefinition) {
        this.readerContext = readerContext;
        this.delegate = delegate;
        this.containingBeanDefinition = containingBeanDefinition;
    }
}
