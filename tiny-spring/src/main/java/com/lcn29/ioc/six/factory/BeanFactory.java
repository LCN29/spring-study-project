package com.lcn29.ioc.six.factory;

import com.lcn29.ioc.six.bean.BeanDefinition;

/**
 * <pre>
 * Bean 容器功能声明接口
 * </pre>
 *
 * @author canxin.li
 * @date 2021-04-21 22:03
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;

}
