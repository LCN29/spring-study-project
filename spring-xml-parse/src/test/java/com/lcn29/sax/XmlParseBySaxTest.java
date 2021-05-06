package com.lcn29.sax;

import com.lcn29.spring.resource.ClassPathResource;
import org.junit.Test;

import java.io.File;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 14:59
 */
public class XmlParseBySaxTest {

    @Test
    public void test() throws Exception {

        // TODO 测试记得把下面的路径修改为自己的
        String xmlFilePath = "/Users/lcn29/spring-study-project/spring-xml-parse/src/test/resources/spring-bean.xml";
        XmlParseBySax.parseXml(xmlFilePath);


    }
}
