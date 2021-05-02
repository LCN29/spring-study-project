package com.lcn29.spring.reader;

import com.lcn29.spring.resource.Resource;
import com.sun.istack.internal.Nullable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 17:29
 */
public class NullSourceExtractor implements SourceExtractor {

    @Override
    public Object extractSource(Object sourceCandidate, Resource definitionResource) {
        return null;
    }
}
