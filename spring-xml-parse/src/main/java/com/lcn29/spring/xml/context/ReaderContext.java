package com.lcn29.spring.xml.context;

import com.lcn29.spring.resource.Resource;
import com.lcn29.spring.support.source.SourceExtractor;

/**
 * <pre>
 * Resource 包装
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 14:54
 */
public class ReaderContext {

    private final Resource resource;

    private final SourceExtractor sourceExtractor;

    public ReaderContext(Resource resource, SourceExtractor sourceExtractor) {
        this.resource = resource;
        this.sourceExtractor = sourceExtractor;
    }

    public final Resource getResource() {
        return this.resource;
    }

    /**
     * 资源绑定到 Resource
     *
     * @param sourceCandidate
     * @return
     */
    public Object extractSource(Object sourceCandidate) {
        return this.sourceExtractor.extractSource(sourceCandidate, this.resource);
    }
}
