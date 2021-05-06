package com.lcn29.spring.registry;

import com.lcn29.spring.bean.definition.AbstractBeanDefinition;
import com.lcn29.spring.bean.definition.BeanDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-06 13:08
 */
public class DefaultListableBeanFactory implements BeanDefinitionRegistry {

    /**
     * beanDefinition 的注册容器
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * 已经注册的 beanName
     */
    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

    /**
     * 是否允许重试注册一个不同的 definition, 使用同一个 bean name
     */
    private boolean allowBeanDefinitionOverriding = true;

    @Override
    public void registerAlias(String name, String alias) {
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {

        if (beanDefinition instanceof AbstractBeanDefinition) {
            try {
                ((AbstractBeanDefinition) beanDefinition).validate();
            } catch (RuntimeException ex) {
                throw new RuntimeException(beanDefinition.getResourceDescription() + beanName + "Validation of bean definition failed", ex);
            }
        }

        BeanDefinition existingDefinition = this.beanDefinitionMap.get(beanName);

        if (existingDefinition != null) {

            // TODO 省略注册
            this.beanDefinitionMap.put(beanName, beanDefinition);
        } else {

            // TODO 各种判断， 是否已经创建等

            this.beanDefinitionMap.put(beanName, beanDefinition);
            this.beanDefinitionNames.add(beanName);
        }
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public boolean isBeanNameInUse(String beanName) {
        // 别名列表等都要判断
        return false;
    }

}
