package com.lcn29.ioc.five.service;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 15:53
 */
public class AnotherServiceImpl {

    private HelloWorldServiceImpl helloWorldService;

    public void show() {
        System.out.println("I'm another service.");
    }
}
