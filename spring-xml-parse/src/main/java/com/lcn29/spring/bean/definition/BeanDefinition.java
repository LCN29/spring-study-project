package com.lcn29.spring.bean.definition;

import com.lcn29.spring.bean.definition.attribute.MutablePropertyValues;
import com.lcn29.spring.bean.definition.constructor.ConstructorArgumentValues;
import com.lcn29.spring.source.BeanMetadataElement;

/**
 * <pre>
 * Bean 的定义, 通过这个对象创建出真正的 Bean
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 15:15
 */
public interface BeanDefinition extends BeanMetadataElement {

    /**
     * 获取 bean 的 class 名
     *
     * @return
     */
    String getBeanClassName();

    /**
     * 设置 bean 的 class 名
     *
     * @param beanClassName
     */
    void setBeanClassName(String beanClassName);

    /**
     * 设置 bean 的作用域
     *
     * @param scope
     */
    void setScope(String scope);

    /**
     * 获取 bean 的作用域, "singleton", "prototype"
     *
     * @return
     */
    String getScope();

    /**
     * 设置是否需要懒加载
     *
     * @param lazyInit
     */
    void setLazyInit(boolean lazyInit);

    /**
     * 获取父级的名称
     *
     * @return
     */
    String getParentName();

    /**
     * 设置父级的名称
     *
     * @param parentName
     */
    void setParentName(String parentName);

    /**
     * 返回工厂 bean 的名
     *
     * @return
     */
    String getFactoryBeanName();

    /**
     * 设置工厂 Bean 的名
     *
     * @param factoryBeanName
     */
    void setFactoryBeanName(String factoryBeanName);

    /**
     * 获取是否需要懒加载
     *
     * @return
     */
    boolean isLazyInit();

    /**
     * 这个 bean 需要依赖哪些 bean
     *
     * @param dependsOn
     */
    void setDependsOn(String... dependsOn);

    /**
     * 获取这个类需要依赖的 bean
     *
     * @return
     */
    String[] getDependsOn();

    /**
     * 设置这个 bean 的在注入别的 bean 时是否忽略
     * <p>
     * 同一个接口有多个实现时，
     * 在 class A  中, 注入这个接口, 没有任何措施时，spring 不知道注入哪一个
     * 但是当这个 bean 设置了 autowire-candidate 表示忽略这个 bean, 查找别的 bean
     *
     * @param autowireCandidate
     */
    void setAutowireCandidate(boolean autowireCandidate);

    /**
     * 是否需要忽略这个bean 的注入
     *
     * @return
     */
    boolean isAutowireCandidate();

    /**
     * 是否为抽象类, 是的话，不会进行初始化
     *
     * @return
     */
    boolean isAbstract();

    /**
     * 获取构造函数列表
     *
     * @return
     */
    ConstructorArgumentValues getConstructorArgumentValues();

    /**
     * 构造函数列数是否为空
     *
     * @return
     */
    default boolean hasConstructorArgumentValues() {
        return !getConstructorArgumentValues().isEmpty();
    }

    /**
     * 获取属性列表
     *
     * @return
     */
    MutablePropertyValues getPropertyValues();

    /**
     * 设置工厂方法
     *
     * @param factoryMethodName
     */
    void setFactoryMethodName(String factoryMethodName);

    /**
     * 获取工厂方法
     *
     * @return
     */
    String getFactoryMethodName();

    /**
     * 资源描述
     *
     * @return
     */
    String getResourceDescription();
}
