package com.lcn29.knowledge.type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *  https://blog.csdn.net/u012881904/article/details/80813294
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-17  18:03
 */
public class ParameterizedTypeTest {

	private Map<String, ParameterizedTypeTest> map;

	private Set<String> set1;

	private Class<?> clz;

	private Holder<String> holder;

	private List<String> list;

	private String str;

	private Integer i;

	private Set set;

	private List aList;

	private Map.Entry<String, String> entry;

	static class Holder<V> {
	}

	public static void testParameterizedType() {

		Field[] fields = ParameterizedTypeTest.class.getDeclaredFields();

		Field f = null;
		for (int i = 0; i < fields.length; i++) {
			f = fields[i];

			if (f.getGenericType() instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) f.getGenericType();
				for (Type type : parameterizedType.getActualTypeArguments()) {
					System.out.println(f.getName() + ": 获取ParameterizedType:" + type);
				}
				if (parameterizedType.getOwnerType() != null) {
					System.out.println(f.getName() + ":getOwnerType:" + parameterizedType.getOwnerType());
				} else {
					System.out.println(f.getName() + ":getOwnerType is null");
				}
				if (parameterizedType.getRawType() != null) {
					System.out.println(f.getName() + ":getRawType:" + parameterizedType.getRawType());
				}
			} else {
				System.out.println(f.getName() + ":is not ParameterizedType ");
			}
		}
	}

	public static void main(String[] args) {
		testParameterizedType();
	}
}
