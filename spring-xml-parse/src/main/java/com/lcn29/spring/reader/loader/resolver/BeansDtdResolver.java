package com.lcn29.spring.reader.loader.resolver;

import com.lcn29.spring.resource.ClassPathResource;
import com.lcn29.spring.resource.Resource;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * <pre>
 * Dtd 解析
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-06 14:28
 */
public class BeansDtdResolver implements EntityResolver {

    private static final String DTD_EXTENSION = ".dtd";

    private static final String DTD_NAME = "spring-beans";

    /**
     * 一般情况下 spring 的 xml 文件用的都是 vsd, 但是其本身也支持 dtd,
     * <p>
     * xml 的配置如下
     * <p>
     * <?xml version="1.0" encoding="UTF-8"?>
     * <!DOCTYPE bean PUBLIC "-//SPRING//DTD BEAN//EN" "http://www.springframework.org/dtd/spring-beans.dtd"/>
     * <beans>
     *
     * </beans>
     *
     * @param publicId
     * @param systemId
     * @return
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {

        // 在上面的 xml 文件中
        // publicId 为 -//SPRING//DTD BEAN//EN
        // systemId 为 http://www.springframework.org/dtd/spring-beans.dtd

        if (systemId != null && systemId.endsWith(DTD_EXTENSION)) {
            int lastPathSeparator = systemId.lastIndexOf('/');
            int dtdNameStart = systemId.indexOf(DTD_NAME, lastPathSeparator);
            if (dtdNameStart != -1) {
                String dtdFile = DTD_NAME + DTD_EXTENSION;
                try {
                    Resource resource = new ClassPathResource(dtdFile, getClass());
                    InputSource source = new InputSource(resource.getInputStream());
                    source.setPublicId(publicId);
                    source.setSystemId(systemId);
                    return source;
                } catch (FileNotFoundException ex) {
                }
            }

        }
        return null;
    }
}
