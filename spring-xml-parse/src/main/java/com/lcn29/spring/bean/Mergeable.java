package com.lcn29.spring.bean;

import com.sun.istack.internal.Nullable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 14:08
 */
public interface Mergeable {

    boolean isMergeEnabled();

    Object merge(@Nullable Object parent);
}
