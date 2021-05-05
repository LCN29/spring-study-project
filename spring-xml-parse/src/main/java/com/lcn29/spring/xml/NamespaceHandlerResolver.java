package com.lcn29.spring.xml;

/**
 * <pre>
 * 命名空间解析接口
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 14:15
 */
public interface NamespaceHandlerResolver {

    NamespaceHandler resolve(String namespaceUri);
}
