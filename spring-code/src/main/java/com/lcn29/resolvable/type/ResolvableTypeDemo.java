package com.lcn29.resolvable.type;

import com.lcn29.resolvable.type.bean.Children;
import org.springframework.core.ResolvableType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-21  9:49
 */
public class ResolvableTypeDemo {

	public static void main(String[] args) {

		Type genericSuperclassType = Children.class.getGenericSuperclass();
		if (genericSuperclassType instanceof ParameterizedType) {
			Type[] actualTypeArguments = ((ParameterizedType) genericSuperclassType).getActualTypeArguments();
			for (Type argumentType : actualTypeArguments) {
				System.out.println("父类泛型参数的具体类型" + argumentType);
			}
		}

		System.out.printf("\n");

		// 接口
		Type[] genericInterfacesTypes = Children.class.getGenericInterfaces();
		for (Type interfaceType : genericInterfacesTypes) {
			if (interfaceType instanceof ParameterizedType) {
				Type[] actualTypeArguments = ((ParameterizedType) interfaceType).getActualTypeArguments();
				for (Type argumentType : actualTypeArguments) {
					System.out.println("接口泛型参数的具体类型" + argumentType);
				}
			}
		}

		System.out.printf("\n");

		ResolvableType superResolvableType = ResolvableType.forClass(Children.class).getSuperType();
		System.out.println("父类泛型参数具体类型" + superResolvableType.resolveGenerics()[0]);

		ResolvableType superInterfaceResolvableType = ResolvableType.forClass(Children.class).getInterfaces()[0];

		System.out.println("接口泛型参数的具体类型" + superInterfaceResolvableType.resolveGenerics()[0]);

		System.out.printf("\n");

		Children[] children = new Children[5];
		System.out.printf("测试" + ResolvableType.forInstance(children).getType());

	}

}
