package com.lcn29.spring.support.source;

import com.lcn29.spring.resource.Resource;

/**
 * <pre>
 * 直接返回 Null 的源绑定
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 15:09
 */
public class NullSourceExtractor implements SourceExtractor {

    @Override
    public Object extractSource(Object sourceCandidate, Resource definitionResource) {
        return null;
    }
}
