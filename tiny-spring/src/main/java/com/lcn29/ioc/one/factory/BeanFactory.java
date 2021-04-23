package com.lcn29.ioc.one.factory;

import com.lcn29.ioc.one.bean.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *  Bean 容器
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-23 17:38
 */
public class BeanFactory {

    /**
     * 存储 bean 定义的容器
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 通过 name 获取到定义在 BeanDefiniton 中的 bean
     * @param name
     * @return
     */
    public Object getBean(String name) {
        return beanDefinitionMap.get(name).getBean();
    }

    /**
     * 注册 BeanDefinition 到容器中
     * @param name key 值
     * @param beanDefinition
     */
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }
}
