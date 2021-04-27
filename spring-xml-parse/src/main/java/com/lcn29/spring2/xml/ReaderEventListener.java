package com.lcn29.spring2.xml;

import java.util.EventListener;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 18:05
 */
public interface ReaderEventListener extends EventListener {

    void defaultsRegistered(DefaultsDefinition defaultsDefinition);
}
