package com.lcn29.spring.reader.loader.handler;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * <pre>
 * 简单的 Sax 异常通知事件
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-06 14:20
 */
public class SimpleSaxErrorHandler implements ErrorHandler {

    public SimpleSaxErrorHandler() {
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        System.err.println("Ignored XML validation warning" + exception.getMessage());
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        throw exception;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        throw exception;
    }
}
