package com.lcn29.spring.registry;

/**
 * <pre>
 * 支持表名的注册容器
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 11:32
 */
public interface AliasRegistry {

    /**
     * 别名注册
     *
     * @param name  bean 名
     * @param alias 别名
     */
    void registerAlias(String name, String alias);
}
