package com.lcn29.spring.util;

import java.net.*;

/**
 * <pre>
 *  Resource 资源工具类
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 18:23
 */
public class ResourceUtils {

    /**
     * 判断指定的路径是否为一个 url
     *
     * @param resourceLocation
     * @return
     */
    public static boolean isUrl(String resourceLocation) {

        if (resourceLocation == null) {
            return false;
        }
        if (resourceLocation.startsWith("classpath:")) {
            return true;
        }
        try {
            new URL(resourceLocation);
            return true;
        } catch (MalformedURLException ex) {
            return false;
        }
    }

    /**
     * 资源路径转为 URI
     *
     * @param location
     * @return
     * @throws URISyntaxException
     */
    public static URI toURI(String location) throws URISyntaxException {
        return new URI(StringUtils.replace(location, " ", "%20"));
    }

    /**
     * 开启 URLConnection 的缓存
     *
     * @param con
     */
    public static void useCachesIfNecessary(URLConnection con) {
        con.setUseCaches(con.getClass().getSimpleName().startsWith("JNLP"));
    }
}
