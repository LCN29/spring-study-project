package com.lcn29.knowledge.type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *  参数化类型，即泛型
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

	private ThreeHolder<String, Long, Date> threeHolder;

	static class Holder<V> {
	}

	static class ThreeHolder<S, T, V>{

	}

	public static void testParameterizedType() {

		Field[] fields = ParameterizedTypeTest.class.getDeclaredFields();

		Field f = null;
		for (int i = 0; i < fields.length; i++) {
			f = fields[i];

			if (f.getGenericType() instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) f.getGenericType();

				// 全部泛型的实际类型
				for (Type type : parameterizedType.getActualTypeArguments()) {
					System.out.println(f.getName() + ": 获取ParameterizedType:" + type);
				}

				// 当前类所在类的类型, 内部类
				if (parameterizedType.getOwnerType() != null) {
					System.out.println(f.getName() + ":getOwnerType:" + parameterizedType.getOwnerType());
				} else {
					System.out.println(f.getName() + ":getOwnerType is null");
				}

				// 当前类的类型
				if (parameterizedType.getRawType() != null) {
					System.out.println(f.getName() + ":getRawType:" + parameterizedType.getRawType());
				}

				System.out.println("\n");

			} else {
				System.out.println(f.getName() + ":is not ParameterizedType ");
				System.out.println("\n");
			}
		}
	}

	public static void main(String[] args) {
		testParameterizedType();
	}
}
