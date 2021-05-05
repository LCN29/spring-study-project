package com.lcn29.spring.bean.definition.method;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <pre>
 * 方法集合封装类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 20:37
 */
public class MethodOverrides {

    /**
     * 方法集合
     */
    private final Set<MethodOverride> overrides = new CopyOnWriteArraySet<>();

    public MethodOverrides() {
    }

    public MethodOverrides(MethodOverrides other) {
        addOverrides(other);
    }

    public void addOverrides(MethodOverrides other) {
        if (other != null) {
            this.overrides.addAll(other.overrides);
        }
    }

    public void addOverride(MethodOverride override) {
        this.overrides.add(override);
    }
}
