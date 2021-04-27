package com.lcn29.spring.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 18:00
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

    Resource createRelative(String relativePath) throws IOException;
}
