package com.lcn29.spring.reader.loader.resolver;

import com.lcn29.spring.resource.Resource;
import com.lcn29.spring.resource.loader.ResourceLoader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * <pre>
 * 带 Resource 的解析器
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-06 15:02
 */
public class ResourceEntityResolver extends DelegatingEntityResolver {

    private final ResourceLoader resourceLoader;

    public ResourceEntityResolver(ResourceLoader resourceLoader) {
        super(resourceLoader.getClassLoader());
        this.resourceLoader = resourceLoader;
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {

        InputSource source = super.resolveEntity(publicId, systemId);

        // 多一层容错
        if (source == null && systemId != null) {
            String resourcePath = null;
            try {
                String decodedSystemId = URLDecoder.decode(systemId, "UTF-8");
                String givenUrl = new URL(decodedSystemId).toString();
                String systemRootUrl = new File("").toURI().toURL().toString();
                // Try relative to resource base if currently in system root.
                if (givenUrl.startsWith(systemRootUrl)) {
                    resourcePath = givenUrl.substring(systemRootUrl.length());
                }
            } catch (Exception ex) {
                resourcePath = systemId;
            }

            if (resourcePath != null) {
                Resource resource = this.resourceLoader.getResource(resourcePath);
                source = new InputSource(resource.getInputStream());
                source.setPublicId(publicId);
                source.setSystemId(systemId);
            } else if (systemId.endsWith(DTD_SUFFIX) || systemId.endsWith(XSD_SUFFIX)) {
                String url = systemId;
                if (url.startsWith("http:")) {
                    url = "https:" + url.substring(5);
                }
                try {
                    source = new InputSource(new URL(url).openStream());
                    source.setPublicId(publicId);
                    source.setSystemId(systemId);
                } catch (IOException ex) {
                    source = null;
                }
            }
        }

        return source;
    }
}
