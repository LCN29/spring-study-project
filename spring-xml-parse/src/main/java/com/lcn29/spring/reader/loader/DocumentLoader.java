package com.lcn29.spring.reader.loader;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

/**
 * <pre>
 * Sax Document 解析接口
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-06 14:02
 */
public interface DocumentLoader {

    /**
     * xml 文件加载
     *
     * @param inputSource    输入源
     * @param entityResolver 自定义的查找解析器
     * @param errorHandler   异常通知 Handler
     * @param validationMode xml 的校验模式
     * @param namespaceAware 命名空间检查
     * @return
     * @throws Exception
     */
    Document loadDocument(InputSource inputSource, EntityResolver entityResolver, ErrorHandler errorHandler, int validationMode, boolean namespaceAware) throws Exception;
}
