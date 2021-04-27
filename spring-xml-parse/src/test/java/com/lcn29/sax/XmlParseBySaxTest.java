package com.lcn29.sax;

import org.junit.Test;

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

        String xmlFilePath = "/Users/lcn/Project/GitHub/spring-study-project/spring-xml-parse/src/test/resources/spring-bean.xml";
        XmlParseBySax.parseXml(xmlFilePath);

    }
}
