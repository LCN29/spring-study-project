package com.lcn29.ioc.six.reader;

/**
 * <pre>
 * Bean 定义读取接口
 * </pre>
 *
 * @author canxin.li
 * @date 2021-04-22 09:27
 */
public interface BeanDefinitionReader {

    /**
     * 通过指定的路径加载资源
     * @param location 路径
     * @throws Exception
     */
    void loadBeanDefinitions(String location) throws Exception;
}
