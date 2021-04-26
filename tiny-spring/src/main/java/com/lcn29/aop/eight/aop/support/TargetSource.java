package com.lcn29.aop.eight.aop.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * 被代理的类
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-25 18:15
 */
@Getter
@AllArgsConstructor
public class TargetSource {

	private Class targetClass;

	private Object target;

	public TargetSource(Object target, Class<?> targetClass) {
		this.target = target;
		this.targetClass = targetClass;
	}

	public Class getTargetClass() {
		return targetClass;
	}

	public Object getTarget() {
		return target;
	}
}
