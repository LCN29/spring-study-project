package com.lcn29.spring.xml;

import com.lcn29.spring.resource.Resource;
import com.sun.istack.internal.Nullable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 18:01
 */
public interface SourceExtractor {

    Object extractSource(Object sourceCandidate, Resource definingResource);
}
