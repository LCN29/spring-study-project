package com.lcn29.spring.reader.loader.resolver;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * <pre>
 * 委托的 Entity 解析器
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-06 14:24
 */
public class DelegatingEntityResolver implements EntityResolver {

    public static final String DTD_SUFFIX = ".dtd";

    public static final String XSD_SUFFIX = ".xsd";

    private final EntityResolver dtdResolver;

    private final EntityResolver schemaResolver;

    public DelegatingEntityResolver(ClassLoader classLoader) {
        this.dtdResolver = new BeansDtdResolver();
        this.schemaResolver = new PluggableSchemaResolver(classLoader);
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {

        if (systemId != null) {
            if (systemId.endsWith(DTD_SUFFIX)) {
                return this.dtdResolver.resolveEntity(publicId, systemId);
            } else if (systemId.endsWith(XSD_SUFFIX)) {
                return this.schemaResolver.resolveEntity(publicId, systemId);
            }
        }

        return null;
    }

}
