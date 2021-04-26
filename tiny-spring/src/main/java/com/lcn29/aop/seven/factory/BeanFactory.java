package com.lcn29.aop.seven.factory;

/**
 * <pre>
 * Bean 容器功能声明接口
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-21 22:03
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;

}
