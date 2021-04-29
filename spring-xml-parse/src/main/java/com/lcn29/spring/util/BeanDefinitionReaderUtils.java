package com.lcn29.spring.util;

import com.lcn29.spring.bean.AbstractBeanDefinition;
import com.lcn29.spring.bean.BeanDefinition;
import com.lcn29.spring.bean.BeanDefinitionHolder;
import com.lcn29.spring.bean.GenericBeanDefinition;
import com.lcn29.spring.registry.BeanDefinitionRegistry;
import com.lcn29.spring.exception.BeanDefinitionStoreException;
import com.sun.istack.internal.Nullable;

/**
 * <pre>
 *  BeanDefinitionReader 读取工具类
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 19:05
 */
public class BeanDefinitionReaderUtils {

    public static void registerBeanDefinition(
            BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry)
            throws BeanDefinitionStoreException {

        // Register bean definition under primary name.
        String beanName = definitionHolder.getBeanName();
        registry.registerBeanDefinition(beanName, definitionHolder.getBeanDefinition());

        // Register aliases for bean name, if any.
        String[] aliases = definitionHolder.getAliases();
        if (aliases != null) {
            for (String alias : aliases) {
                registry.registerAlias(beanName, alias);
            }
        }
    }

    public static String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry, boolean isInnerBean)
            throws BeanDefinitionStoreException {

        String generatedBeanName = definition.getBeanClassName();
        if (generatedBeanName == null) {
            if (definition.getParentName() != null) {
                generatedBeanName = definition.getParentName() + "$child";
            } else if (definition.getFactoryBeanName() != null) {
                generatedBeanName = definition.getFactoryBeanName() + "$created";
            }
        }
        if (!StringUtils.hasText(generatedBeanName)) {
            throw new BeanDefinitionStoreException("Unnamed bean definition specifies neither " +
                    "'class' nor 'parent' nor 'factory-bean' - can't generate bean name");
        }

        String id = generatedBeanName;
        if (isInnerBean) {
            // Inner bean: generate identity hashcode suffix.
            id = generatedBeanName + "#" + ObjectUtils.getIdentityHexString(definition);
        } else {
            // Top-level bean: use plain class name with unique suffix if necessary.
            return uniqueBeanName(generatedBeanName, registry);
        }
        return id;
    }

    public static String uniqueBeanName(String beanName, BeanDefinitionRegistry registry) {
        String id = beanName;
        int counter = -1;

        // Increase counter until the id is unique.
        while (counter == -1 || registry.containsBeanDefinition(id)) {
            counter++;
            id = beanName + "#" + counter;
        }
        return id;
    }

    public static AbstractBeanDefinition createBeanDefinition(String parentName, String className, ClassLoader classLoader) throws ClassNotFoundException {

        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setParentName(parentName);
        if (className != null) {
            if (classLoader != null) {
                bd.setBeanClass(ClassUtils.forName(className, classLoader));
            } else {
                bd.setBeanClassName(className);
            }
        }
        return bd;
    }
}
