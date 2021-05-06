package com.lcn29.spring.bean.definition;

import com.lcn29.spring.bean.definition.attribute.MutablePropertyValues;
import com.lcn29.spring.bean.definition.constructor.ConstructorArgumentValues;
import com.lcn29.spring.bean.definition.method.MethodOverride;
import com.lcn29.spring.bean.definition.method.MethodOverrides;
import com.lcn29.spring.bean.definition.qualifier.AutowireCandidateQualifier;
import com.lcn29.spring.resource.Resource;
import com.lcn29.spring.support.attribute.BeanMetadataAttributeAccessor;
import com.lcn29.spring.util.ClassUtils;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 * 抽象的 Bean 定义
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 20:33
 */
@Data
public abstract class AbstractBeanDefinition extends BeanMetadataAttributeAccessor implements BeanDefinition {

    /**
     * bean 的 class 类型, 可能为 String, 也可能为 Class
     */
    private volatile Object beanClass;

    /**
     * bean 的作用域
     */
    private String scope = "";

    /**
     * 是否懒加载
     */
    private boolean lazyInit = false;

    /**
     * 是否为抽象类
     */
    private boolean abstractFlag = false;

    /**
     * bean 工厂的名
     */
    private String factoryBeanName;

    /**
     * 自定义的描述
     */
    private String description;

    /**
     * 从哪个资源加载的
     */
    private Resource resource;

    /**
     * 是否需要进行依赖检查
     */
    private int dependencyCheck = 0;

    /**
     * 自动导入模式
     */
    private int autowireMode = 0;

    /**
     * 依赖数组
     */
    private String[] dependsOn;

    /**
     * 是否为注入忽略
     */
    private boolean autowireCandidate = true;

    /**
     * 构造函数参数集合
     */
    private ConstructorArgumentValues constructorArgumentValues;

    /**
     * 属性集合
     */
    private MutablePropertyValues propertyValues;

    /**
     * 工厂方法
     */
    private String factoryMethodName;

    /**
     * 方法集合
     */
    private MethodOverrides methodOverrides = new MethodOverrides();

    /**
     * qualifier 标签集合
     */
    private final Map<String, AutowireCandidateQualifier> qualifiers = new LinkedHashMap<>();

    /**
     * 获取 Class
     *
     * @return
     * @throws IllegalStateException
     */
    public Class<?> getBeanClass() throws IllegalStateException {
        Object beanClassObject = this.beanClass;
        if (beanClassObject == null) {
            throw new IllegalStateException("No bean class specified on bean definition");
        }
        if (!(beanClassObject instanceof Class)) {
            throw new IllegalStateException(
                    "Bean class name [" + beanClassObject + "] has not been resolved into an actual Class");
        }
        return (Class<?>) beanClassObject;
    }

    @Override
    public String getBeanClassName() {
        Object beanClassObject = this.beanClass;
        if (beanClassObject instanceof Class) {
            return ((Class<?>) beanClassObject).getName();
        } else {
            return (String) beanClassObject;
        }
    }

    @Override
    public void setBeanClassName(String beanClassName) {
        this.beanClass = beanClassName;
    }

    @Override
    public boolean isAbstract() {
        return this.abstractFlag;
    }

    public void setAbstract(Boolean abstractFlag) {
        this.abstractFlag = abstractFlag;
    }

    /**
     * 添加 qualifier
     *
     * @param qualifier
     */
    public void addQualifier(AutowireCandidateQualifier qualifier) {
        this.qualifiers.put(qualifier.getTypeName(), qualifier);
    }

    /**
     * 校验这个 beanDefinition
     */
    public void validate() {
        if (hasMethodOverrides() && getFactoryMethodName() != null) {
            throw new RuntimeException("Cannot combine factory method with container-generated method overrides: " +
                    "the factory method must create the concrete bean instance.");
        }
        if (hasBeanClass()) {
            prepareMethodOverrides();
        }
    }

    /**
     * 判断这个 beanDefinition 是否有方法集合
     *
     * @return
     */
    public boolean hasMethodOverrides() {
        return !this.methodOverrides.isEmpty();
    }

    @Override
    public String getFactoryMethodName() {
        return this.factoryMethodName;
    }

    @Override
    public String getResourceDescription() {
        return (this.resource != null ? this.resource.getDescription() : null);
    }

    @Override
    public void setFactoryMethodName(String factoryMethodName) {
        this.factoryMethodName = factoryMethodName;
    }

    /**
     * 已经有 bean 对应的 class 了
     *
     * @return
     */
    public boolean hasBeanClass() {
        return (this.beanClass instanceof Class);
    }

    /**
     * 检查方法存在
     */
    public void prepareMethodOverrides() {
        if (hasMethodOverrides()) {
            getMethodOverrides().getOverrides().forEach(this::prepareMethodOverride);
        }
    }

    /**
     * 检查方法
     *
     * @param mo
     */
    protected void prepareMethodOverride(MethodOverride mo) {
        int count = ClassUtils.getMethodCountForName(getBeanClass(), mo.getMethodName());
        if (count == 0) {
            throw new RuntimeException("Invalid method override: no method with name '" + mo.getMethodName() +
                    "' on class [" + getBeanClassName() + "]");
        } else if (count == 1) {
            mo.setOverloaded(false);
        }
    }
}
