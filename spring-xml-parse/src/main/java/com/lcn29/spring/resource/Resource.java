package com.lcn29.spring.resource;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 20:35
 */
public interface Resource extends InputStreamSource {

    /**
     * 描述
     *
     * @return
     */
    String getDescription();

}
