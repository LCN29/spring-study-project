package com.lcn29.spring.support.state;

import java.util.LinkedList;

/**
 * <pre>
 * xml 解析状态
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 16:22
 */
public final class ParseState {

    private final LinkedList<Entry> state;

    public ParseState() {
        this.state = new LinkedList<>();
    }

    private ParseState(ParseState other) {
        this.state = (LinkedList<Entry>) other.state.clone();
    }

    public void push(Entry entry) {
        this.state.push(entry);
    }

    public void pop() {
        this.state.pop();
    }

    public Entry peek() {
        return this.state.peek();
    }

    public ParseState snapshot() {
        return new ParseState(this);
    }

    public interface Entry {
    }
}
