package com.lcn29.spring.resource.loader;


import com.lcn29.spring.resource.Resource;

/**
 * <pre>
 * 资源加载器接口
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-02 17:39
 */
public interface ResourceLoader {

    Resource getResource(String location);

    ClassLoader getClassLoader();
}
