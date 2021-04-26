package com.lcn29.aop.seven.context;


import com.lcn29.aop.seven.factory.AbstractBeanFactory;

/**
 * <pre>
 * 抽象的上下文
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-22 16:09
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    protected AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 上下文刷新
     * @throws Exception
     */
    public void refresh() throws Exception{
    }

    /**
     * 获取 Bean
     * @param name
     * @return
     * @throws Exception
     */
    @Override
    public Object getBean(String name) throws Exception {
        return beanFactory.getBean(name);
    }
}
