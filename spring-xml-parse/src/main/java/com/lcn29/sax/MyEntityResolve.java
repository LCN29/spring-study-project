package com.lcn29.sax;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-06 17:11
 */
public class MyEntityResolve implements EntityResolver {


    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        // 没有任何实现，默认返回 null 即可
        return null;
    }
}
