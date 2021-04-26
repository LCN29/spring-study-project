package com.lcn29.aop.seven.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 *  资源接口定义
 * </pre>
 *
 * @author lcn29
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
