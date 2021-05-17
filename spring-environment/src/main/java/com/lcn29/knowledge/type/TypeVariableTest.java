package com.lcn29.knowledge.type;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.List;

/**
 * <pre>
 * 泛型的类型变量, List<T>, Map<K,V> 值得就是 T, K, V 这些值
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-17 21:56
 */
public class TypeVariableTest<T extends Number & Serializable, V> {

    private T key;

    private V value;

    private V[] values;

    private String str;

    private List<T> tList;

    public static void testTypeVariableTest() {

        Field f = null;
        Field[] fields = TypeVariableTest.class.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            f = fields[i];

            if (f.getGenericType() instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) f.getGenericType();

                for (Type type : parameterizedType.getActualTypeArguments()) {
                    System.out.println(f.getName() + ": 获取ParameterizedType:" + type);
                    if (type instanceof TypeVariable) {
                        printTypeVariable(f.getName(), (TypeVariable) type);
                    }
                }
                continue;
            }

            if (f.getGenericType() instanceof GenericArrayType) {

                GenericArrayType genericArrayType = (GenericArrayType) f.getGenericType();
                System.out.println("GenericArrayType type :" + genericArrayType);

                Type genericComponentType = genericArrayType.getGenericComponentType();
                if (genericComponentType instanceof TypeVariable) {
                    printTypeVariable(f.getName(), (TypeVariable) genericComponentType);
                }
                continue;
            }

            if (f.getGenericType() instanceof TypeVariable) {
                printTypeVariable(f.getName(), (TypeVariable) f.getGenericType());
                continue;
            }

            System.out.println("type :" + f.getGenericType());
            System.out.println("\n");
        }
    }

    private static void printTypeVariable(String fieldName, TypeVariable typeVariable) {

        // 上限, 默认为 Object
        for (Type type : typeVariable.getBounds()) {
            System.out.println(fieldName + ": TypeVariable getBounds " + type);
        }

        // 声明该类型的类型
        System.out.println("定义 Class getGenericDeclaration: " + typeVariable.getGenericDeclaration());
        System.out.println("getName: " + typeVariable.getName());
        System.out.println("\n");
    }

    public static void main(String[] args) {
        testTypeVariableTest();
    }
}
