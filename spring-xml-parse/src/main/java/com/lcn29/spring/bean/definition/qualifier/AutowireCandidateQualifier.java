package com.lcn29.spring.bean.definition.qualifier;

import com.lcn29.spring.bean.definition.attribute.BeanMetadataAttributeAccessor;

/**
 * <pre>
 * bean 注入修饰器
 *
 * 主要用于处理 xml 中 bean  的这种情况
 * <bean id="ada" value="">
 *     <qualifier type="org.springframework.beans.factory.annotation.Qualifier"  value="限定标识符">
 *          <attribute key="" value=""/>
 *     </qualifier>
 *     <qualifier type="org.springframework.beans.factory.annotation.Qualifier2" value="xxxx" />
 * </bean>
 *
 * 在使用 Spring 框架中进行自动注人时，Spring 容器中匹配的候选 Bean 数目必须有且仅有一个。当找不到一个匹配的 Bean 时，Spring 容器将抛出
 * BeanCreationException 异常。Spring 允许我们通过 qualifier 指定注入 Bean 的名称，这样歧义就消除了。
 *
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 20:48
 */
public class AutowireCandidateQualifier extends BeanMetadataAttributeAccessor {

    public static final String VALUE_KEY = "value";

    private final String typeName;

    public AutowireCandidateQualifier(Class<?> type) {
        this(type.getName());
    }

    public AutowireCandidateQualifier(String typeName) {
        this.typeName = typeName;
    }

    public AutowireCandidateQualifier(Class<?> type, Object value) {
        this(type.getName(), value);
    }

    public AutowireCandidateQualifier(String typeName, Object value) {
        this.typeName = typeName;
        setAttribute(VALUE_KEY, value);
    }

    public String getTypeName() {
        return this.typeName;
    }
}
