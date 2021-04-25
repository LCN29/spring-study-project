package com.lcn29.ioc.five.service;

import java.util.Optional;

/**
 * <pre>
 * Hello world 服务类
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-23 17:58
 */
public class HelloWorldServiceImpl {

    private final String HELLO_WORD = "Hello Word !";

    private String anotherWorld;

    private AnotherServiceImpl anotherService;

    public void helloWorld(){

        System.out.print(HELLO_WORD + " ");
        Optional.ofNullable(anotherWorld).ifPresent(System.out::println);
        Optional.ofNullable(anotherService).ifPresent(AnotherServiceImpl::show);
    }

    public void setAnotherWorld(String anotherWorld) {
        this.anotherWorld = anotherWorld;
    }

}
