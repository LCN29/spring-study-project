package com.lcn29.spring.xml;

import com.lcn29.spring.resource.Resource;
import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 17:38
 */
@Data
public class XmlReaderContext {

    private final Resource resource;

    private final SourceExtractor sourceExtractor;

    private final ReaderEventListener eventListener;

    private final XmlBeanDefinitionReader reader;

    public XmlReaderContext(Resource resource, SourceExtractor sourceExtractor, ReaderEventListener eventListener, XmlBeanDefinitionReader reader) {
        this.resource = resource;
        this.sourceExtractor = sourceExtractor;
        this.eventListener = eventListener;
        this.reader = reader;
    }

    public Object extractSource(Object sourceCandidate) {
        return this.sourceExtractor.extractSource(sourceCandidate, this.resource);
    }

    public void fireDefaultsRegistered(DefaultsDefinition defaultsDefinition) {
        this.eventListener.defaultsRegistered(defaultsDefinition);
    }
}
