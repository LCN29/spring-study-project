package com.lcn29.spring.support.state;

/**
 * <pre>
 * 表示解析构造参数中的 Entry
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 17:33
 */
public class ConstructorArgumentEntry implements ParseState.Entry {

    /**
     * 位置
     */
    private final int index;

    public ConstructorArgumentEntry() {
        this.index = -1;
    }

    public ConstructorArgumentEntry(int index) {
        this.index = index;
    }
}
