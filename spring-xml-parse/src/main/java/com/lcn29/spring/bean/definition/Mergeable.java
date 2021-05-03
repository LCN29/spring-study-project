package com.lcn29.spring.bean.definition;

/**
 * <pre>
 * 是否可以合并的接口
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 15:52
 */
public interface Mergeable {

    /**
     * 是否可以合并
     *
     * @return
     */
    boolean isMergeEnabled();

    /**
     * 合并操作
     *
     * @param parent
     * @return
     */
    Object merge(Object parent);
}
