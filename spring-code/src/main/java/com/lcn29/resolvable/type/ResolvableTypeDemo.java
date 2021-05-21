package com.lcn29.resolvable.type;

import com.lcn29.resolvable.type.bean.Children;

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

		Type[] genericInterfacesTypes = Children.class.getGenericInterfaces();
		for (Type interfaceType : genericInterfacesTypes) {
			if (interfaceType instanceof ParameterizedType) {
				Type[] actualTypeArguments = ((ParameterizedType) interfaceType).getActualTypeArguments();
				for (Type argumentType : actualTypeArguments) {
					System.out.println("父接口ParameterizedType.getActualTypeArguments:" + argumentType);
				}
			}
		}


	}

}
