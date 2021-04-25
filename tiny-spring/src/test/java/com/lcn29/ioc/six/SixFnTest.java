package com.lcn29.ioc.six;

import com.lcn29.ioc.six.context.ApplicationContext;
import com.lcn29.ioc.six.context.ClassPathXmlApplicationContext;
import com.lcn29.ioc.six.service.HelloWorldServiceImpl;
import org.junit.Test;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 16:23
 */
public class SixFnTest {

    @Test
    public void test() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc/six/bean.xml");
        HelloWorldServiceImpl helloWorldService = (HelloWorldServiceImpl) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }
}
