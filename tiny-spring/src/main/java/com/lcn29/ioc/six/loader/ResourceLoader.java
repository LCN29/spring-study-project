package com.lcn29.ioc.six.loader;

import com.lcn29.ioc.six.resource.Resource;
import com.lcn29.ioc.six.resource.UrlResource;

import java.net.URL;

/**
 * <pre>
 *  资源加载器
 * </pre>
 *
 * @author canxin.li
 * @date 2021-04-22 09:35
 */
public class ResourceLoader {

    public Resource getResource(String location){
        URL resource = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(resource);
    }
}
