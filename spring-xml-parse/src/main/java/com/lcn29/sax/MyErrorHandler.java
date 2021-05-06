package com.lcn29.sax;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-06 17:12
 */
public class MyErrorHandler implements ErrorHandler {

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        System.out.println("warning异常--->" + exception.getMessage());
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        System.out.println("error 异常--->" + exception.getMessage());
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        System.out.println("fatal 异常--->" + exception.getMessage());
    }
}
