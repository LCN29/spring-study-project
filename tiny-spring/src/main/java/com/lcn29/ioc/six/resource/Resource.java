package com.lcn29.ioc.six.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 *  资源接口定义
 * </pre>
 *
 * @author canxin.li
 * @date 2021-04-22 09:29
 */
public interface Resource {

    /**
     * 获取资源的流
     * @return
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;
}
