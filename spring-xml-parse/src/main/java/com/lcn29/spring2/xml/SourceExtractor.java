package com.lcn29.spring2.xml;

import com.lcn29.spring2.resource.Resource;

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
