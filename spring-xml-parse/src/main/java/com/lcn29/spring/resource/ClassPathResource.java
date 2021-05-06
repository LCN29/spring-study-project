package com.lcn29.spring.resource;

import com.lcn29.spring.util.ClassUtils;
import com.lcn29.spring.util.StringUtils;
import com.sun.istack.internal.Nullable;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 * class 路径的资源
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-06 14:34
 */
@Data
public class ClassPathResource extends AbstractFileResolvingResource {

    /**
     * 资源路径
     */
    private final String path;

    /**
     * 类加载器
     */
    private ClassLoader classLoader;

    /**
     * 调用的 class
     */
    private Class<?> clazz;

    public ClassPathResource(String path, Class<?> clazz) {
        this.path = StringUtils.cleanPath(path);
        this.clazz = clazz;
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        String pathToUse = StringUtils.cleanPath(path);
        if (pathToUse.startsWith("/")) {
            pathToUse = pathToUse.substring(1);
        }
        this.path = pathToUse;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is;
        if (this.clazz != null) {
            is = this.clazz.getResourceAsStream(this.path);
        } else if (this.classLoader != null) {
            is = this.classLoader.getResourceAsStream(this.path);
        } else {
            is = ClassLoader.getSystemResourceAsStream(this.path);
        }
        if (is == null) {
            throw new FileNotFoundException(getDescription() + " cannot be opened because it does not exist");
        }
        return is;
    }

    @Override
    public String getDescription() {
        StringBuilder builder = new StringBuilder("class path resource [");
        String pathToUse = this.path;
        if (this.clazz != null && !pathToUse.startsWith("/")) {
            builder.append(ClassUtils.classPackageAsResourcePath(this.clazz));
            builder.append('/');
        }
        if (pathToUse.startsWith("/")) {
            pathToUse = pathToUse.substring(1);
        }
        builder.append(pathToUse);
        builder.append(']');
        return builder.toString();
    }

}
