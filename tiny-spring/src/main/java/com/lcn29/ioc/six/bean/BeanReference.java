package com.lcn29.ioc.six.bean;

/**
 * <pre>
 * 对象 bean 引用的属性的声明定义
 * </pre>
 *
 * @author canxin.li
 * @date 2021-04-22 11:58
 */
public class BeanReference {

    private String name;

    private Object bean;

    public BeanReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
