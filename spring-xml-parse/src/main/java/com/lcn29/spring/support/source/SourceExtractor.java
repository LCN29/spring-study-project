package com.lcn29.spring.support.source;


import com.lcn29.spring.resource.Resource;

/**
 * <pre>
 * 元数据和 Resource 绑定接口
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 14:57
 */
@FunctionalInterface
public interface SourceExtractor {

    /**
     * 元数据源绑定到 Resource
     *
     * @param sourceCandidate  元数据源
     * @param definingResource 资源
     * @return
     */
    Object extractSource(Object sourceCandidate, Resource definingResource);
}
