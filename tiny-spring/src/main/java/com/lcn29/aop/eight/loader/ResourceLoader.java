package com.lcn29.aop.eight.loader;

import com.lcn29.aop.seven.resource.Resource;
import com.lcn29.aop.seven.resource.UrlResource;

import java.net.URL;

/**
 * <pre>
 *  资源加载器
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-22 09:35
 */
public class ResourceLoader {

    public Resource getResource(String location){
        URL resource = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(resource);
    }
}
