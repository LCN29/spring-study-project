package com.lcn29.knowledge.type;

import java.lang.reflect.*;
import java.util.List;

/**
 * <pre>
 * 通配符, List<?>,  Set<? extends Object>
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-17 22:14
 */
public class WildcardTypeTest {

    private List<? extends Number> a;

    private List<? super String> b;

    private List<String> c;

    private Class<?> aClass;

    private String str;

    public static void testWildcardType() {

        Field[] fields = WildcardTypeTest.class.getDeclaredFields();

        Field f = null;
        for (int i = 0; i < fields.length; i++) {
            f = fields[i];

            if (f.getGenericType() instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) f.getGenericType();
                for (Type type : parameterizedType.getActualTypeArguments()) {
                    System.out.println(f.getName() + ": 获取ParameterizedType:" + type);
                    if (type instanceof WildcardType) {
                        printWildcardType((WildcardType) type);
                    }
                }
                continue;
            }

            if (f.getGenericType() instanceof GenericArrayType) {
                GenericArrayType genericArrayType = (GenericArrayType) f.getGenericType();
                System.out.println("GenericArrayType type :" + genericArrayType);
                Type genericComponentType = genericArrayType.getGenericComponentType();
                if (genericComponentType instanceof WildcardType) {
                    printWildcardType((WildcardType) genericComponentType);
                }
                continue;
            }

            if (f.getGenericType() instanceof TypeVariable) {
                TypeVariable typeVariable = (TypeVariable) f.getGenericType();
                System.out.println("typeVariable:" + typeVariable);
                System.out.println("\n");
                continue;
            }

            System.out.println("type :" + f.getGenericType());
            if (f.getGenericType() instanceof WildcardType) {
                printWildcardType((WildcardType) f.getGenericType());
            }
        }

    }

    private static void printWildcardType(WildcardType wildcardType) {
        for (Type type : wildcardType.getUpperBounds()) {
            System.out.println("上界：" + type);
        }
        for (Type type : wildcardType.getLowerBounds()) {
            System.out.println("下界：" + type);
        }

        System.out.println("\n");
    }

    public static void main(String[] args) {
        testWildcardType();
    }
}
