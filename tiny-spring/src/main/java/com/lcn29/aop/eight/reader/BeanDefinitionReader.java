package com.lcn29.aop.eight.reader;

/**
 * <pre>
 * Bean 定义读取接口
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-22 09:27
 */
public interface BeanDefinitionReader {

    void loadBeanDefinitions(String location) throws Exception;
}
