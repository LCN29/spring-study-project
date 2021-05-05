package com.lcn29.spring.util;

import com.lcn29.spring.bean.definition.AbstractBeanDefinition;
import com.lcn29.spring.bean.definition.BeanDefinition;
import com.lcn29.spring.bean.definition.GenericBeanDefinition;
import com.lcn29.spring.bean.definition.holder.BeanDefinitionHolder;
import com.lcn29.spring.registry.BeanDefinitionRegistry;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 11:34
 */
public class BeanDefinitionReaderUtils {

    /**
     * @param definitionHolder
     * @param registry
     */
    public static void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry) {

        String beanName = definitionHolder.getBeanName();
        registry.registerBeanDefinition(beanName, definitionHolder.getBeanDefinition());

        // 注册别名
        String[] aliases = definitionHolder.getAliases();
        if (aliases != null) {
            for (String alias : aliases) {
                registry.registerAlias(beanName, alias);
            }
        }

    }

    /**
     * 生成 Bean Name
     *
     * @param beanDefinition
     * @param registry
     * @return
     */
    public static String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        return generateBeanName(beanDefinition, registry, false);
    }

    /**
     * 生成 bean Name
     *
     * @param definition
     * @param registry
     * @param isInnerBean 内部Bean
     * @return
     */
    public static String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry, boolean isInnerBean) {

        String generatedBeanName = definition.getBeanClassName();
        if (generatedBeanName == null) {
            if (definition.getParentName() != null) {
                generatedBeanName = definition.getParentName() + "$child";
            } else if (definition.getFactoryBeanName() != null) {
                generatedBeanName = definition.getFactoryBeanName() + "$created";
            }
        }

        if (!StringUtils.hasText(generatedBeanName)) {
            throw new RuntimeException("Unnamed bean definition specifies neither 'class' nor 'parent' nor 'factory-bean' - can't generate bean name");
        }

        String id = generatedBeanName;
        if (isInnerBean) {
            id = generatedBeanName + "#" + ObjectUtils.getIdentityHexString(definition);
        } else {
            return uniqueBeanName(generatedBeanName, registry);
        }
        return id;
    }


    /**
     * 生成唯一的 bean 名
     *
     * @param beanName
     * @param registry
     * @return
     */
    public static String uniqueBeanName(String beanName, BeanDefinitionRegistry registry) {
        String id = beanName;
        int counter = -1;

        while (counter == -1 || registry.containsBeanDefinition(id)) {
            counter++;
            id = beanName + "#" + counter;
        }
        return id;
    }

    /**
     * 创建 BeanDefinition
     * @param parentName
     * @param className
     * @param classLoader
     * @return
     * @throws ClassNotFoundException
     */
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
