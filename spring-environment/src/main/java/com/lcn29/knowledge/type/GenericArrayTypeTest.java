package com.lcn29.knowledge.type;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * <pre>
 * 泛型数组, 特指 T[]  A<T>[](其中 A, 可以为 List, Set 等)
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-17 21:45
 */
public class GenericArrayTypeTest<T> {

    public void testGenericArrayType(List<String>[] pTypeArray, T[] vTypeArray, List<String> list, String[] strings, GenericArrayTypeTest[] test, GenericArrayTypeTest<String>[] test2) {
    }

    public static void testGenericArrayType() {
        Method[] declaredMethods = GenericArrayTypeTest.class.getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (method.getName().startsWith("main")) {
                continue;
            }

            Type[] types = method.getGenericParameterTypes();

            for (Type type : types) {
                if (!(type instanceof GenericArrayType)) {
                    System.out.println(type + "is not GenericArrayType, " + type.getTypeName());
                    System.out.println("\n");
                    continue;
                }

                // 多维数组，这个方法只会降一维, 然后返回, 比如 T[], 返回 T, T[][][], 返回 T[][]
                Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
                System.out.println("genericComponentType:" + genericComponentType);
                System.out.println("\n");
            }
        }
    }

    public static void main(String[] args) {
        testGenericArrayType();
    }


}
