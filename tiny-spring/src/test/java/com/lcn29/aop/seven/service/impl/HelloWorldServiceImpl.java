package com.lcn29.aop.seven.service.impl;

import com.lcn29.aop.seven.service.HelloWorldService;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 18:27
 */
public class HelloWorldServiceImpl implements HelloWorldService {

    private final String HELLO_WORD = "Hello Word !";

    @Override
    public void helloWorld() {
        System.out.println(HELLO_WORD + " Seven. ");
    }
}
