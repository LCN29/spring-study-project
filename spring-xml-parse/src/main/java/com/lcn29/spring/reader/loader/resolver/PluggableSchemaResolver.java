package com.lcn29.spring.reader.loader.resolver;

import com.lcn29.spring.resource.ClassPathResource;
import com.lcn29.spring.resource.Resource;
import com.lcn29.spring.util.CollectionUtils;
import com.lcn29.spring.util.PropertiesLoaderUtils;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * xsd 解析
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-06 14:53
 */
public class PluggableSchemaResolver implements EntityResolver {

    public static final String DEFAULT_SCHEMA_MAPPINGS_LOCATION = "META-INF/spring.schemas";

    /**
     * 校验文件缓存
     */
    private volatile Map<String, String> schemaMappings;

    /**
     * 校验文件路径
     */
    private final String schemaMappingsLocation;


    private final ClassLoader classLoader;

    public PluggableSchemaResolver(ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.schemaMappingsLocation = DEFAULT_SCHEMA_MAPPINGS_LOCATION;
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws IOException {

        if (systemId != null) {
            String resourceLocation = getSchemaMappings().get(systemId);
            if (resourceLocation == null && systemId.startsWith("https:")) {
                // Retrieve canonical http schema mapping even for https declaration
                resourceLocation = getSchemaMappings().get("http:" + systemId.substring(6));
            }

            if (resourceLocation != null) {
                Resource resource = new ClassPathResource(resourceLocation, this.classLoader);

                try {
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

    /**
     * 加载校验的文件
     *
     * @return
     */
    private Map<String, String> getSchemaMappings() {

        Map<String, String> schemaMappings = this.schemaMappings;
        if (schemaMappings == null) {
            synchronized (this) {
                schemaMappings = this.schemaMappings;
                if (schemaMappings == null) {
                    try {


                        // 将 META-INF/spring.schemas 中的内容加载到 mapings
                        // spring.schemas 中的内容都是这种格式的：
                        // http\://www.springframework.org/schema/beans/spring-beans-4.2.xsd=org/springframework/beans/factory/xml/spring-beans.xsd
                        // 可以解析为 key value

                        Properties mappings = PropertiesLoaderUtils.loadAllProperties(this.schemaMappingsLocation, this.classLoader);
                        schemaMappings = new ConcurrentHashMap<>(mappings.size());
                        CollectionUtils.mergePropertiesIntoMap(mappings, schemaMappings);
                        this.schemaMappings = schemaMappings;
                    } catch (IOException e) {
                        throw new IllegalStateException("Unable to load schema mappings from location [" + this.schemaMappingsLocation + "]", e);
                    }
                }
            }

        }
        return schemaMappings;
    }

}
