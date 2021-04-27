package com.lcn29.spring2.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 18:23
 */
public class ResourceUtils {

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
        }
        catch (MalformedURLException ex) {
            return false;
        }
    }

    public static URI toURI(String location) throws URISyntaxException {
        return new URI(StringUtils.replace(location, " ", "%20"));
    }
}
