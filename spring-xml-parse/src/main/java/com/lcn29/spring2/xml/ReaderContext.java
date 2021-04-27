package com.lcn29.spring2.xml;

import com.lcn29.spring2.resource.Resource;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 19:19
 */
public interface ReaderContext {

    private final Resource resource;


    private final ReaderEventListener eventListener;

    private final SourceExtractor sourceExtractor;

    public ReaderContext(Resource resource,
                         ReaderEventListener eventListener, SourceExtractor sourceExtractor) {

        this.resource = resource;
        this.eventListener = eventListener;
        this.sourceExtractor = sourceExtractor;
    }
}
