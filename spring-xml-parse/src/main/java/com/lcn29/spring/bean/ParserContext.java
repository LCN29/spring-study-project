package com.lcn29.spring.bean;

import com.lcn29.spring.reader.BeanDefinitionParserDelegate;
import com.lcn29.spring.reader.XmlReaderContext;
import com.sun.istack.internal.Nullable;
import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 17:02
 */
@Data
public class ParserContext {

    private final XmlReaderContext readerContext;

    private final BeanDefinitionParserDelegate delegate;

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
