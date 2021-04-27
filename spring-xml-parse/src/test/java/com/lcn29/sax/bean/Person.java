package com.lcn29.sax.bean;

import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-27 15:23
 */
@Data
public class Person {

	private String name;

	private int age;

	public void selfIntroduction() {
		System.out.println("Hi, my name is " + name + " and age is " + age);
	}
}
