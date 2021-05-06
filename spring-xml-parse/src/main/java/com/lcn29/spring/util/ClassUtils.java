package com.lcn29.spring.util;

import com.sun.istack.internal.Nullable;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <pre>
 * Class 工具类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 15:49
 */
public class ClassUtils {

    /**
     * 原始数据类型和其数组的缓存
     */
    private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<>(32);

    /**
     * 一些常用到的缓存, 如包装类型, 异常等
     */
    private static final Map<String, Class<?>> commonClassCache = new HashMap<>(64);

    private static final char PACKAGE_SEPARATOR = '.';

    private static final char PATH_SEPARATOR = '/';

    static {

        Set<Class<?>> primitiveTypes = new HashSet<>(32);
        primitiveTypes.add(boolean.class);
        primitiveTypes.add(byte.class);
        primitiveTypes.add(char.class);
        primitiveTypes.add(double.class);
        primitiveTypes.add(float.class);
        primitiveTypes.add(int.class);
        primitiveTypes.add(long.class);
        primitiveTypes.add(short.class);
        primitiveTypes.add(void.class);
        Collections.addAll(primitiveTypes, boolean[].class, byte[].class, char[].class,
                double[].class, float[].class, int[].class, long[].class, short[].class);

        for (Class<?> primitiveType : primitiveTypes) {
            primitiveTypeNameMap.put(primitiveType.getName(), primitiveType);
        }

        // 包装类型
        registerCommonClasses(Boolean.class, Byte.class, Character.class, Double.class, Float.class, Integer.class, Long.class,
                Short.class, Void.class);

        // 包装类型的数组
        registerCommonClasses(Boolean[].class, Byte[].class, Character[].class, Double[].class,
                Float[].class, Integer[].class, Long[].class, Short[].class);

        // 常用到的几个类
        registerCommonClasses(Number.class, Number[].class, String.class, String[].class,
                Class.class, Class[].class, Object.class, Object[].class);

        // 常用到的异常
        registerCommonClasses(Throwable.class, Exception.class, RuntimeException.class,
                Error.class, StackTraceElement.class, StackTraceElement[].class);

        // 常用的几个类
        registerCommonClasses(Enum.class, Iterable.class, Iterator.class, Enumeration.class,
                Collection.class, List.class, Set.class, Map.class, Map.Entry.class, Optional.class);
    }

    /**
     * 通过 class 字符串获取 Class 对象
     *
     * @param name        class 字符串
     * @param classLoader 加载器
     * @return
     * @throws ClassNotFoundException
     * @throws LinkageError
     */
    public static Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {

        // 判断原生数据类似是否存在
        Class<?> clazz = resolvePrimitiveClassName(name);
        if (clazz == null) {
            // 共用缓存是否存储
            clazz = commonClassCache.get(name);
        }
        if (clazz != null) {
            return clazz;
        }

        // "java.lang.String[]" 风格的数组
        if (name.endsWith("[]")) {
            String elementClassName = name.substring(0, name.length() - "[]".length());
            Class<?> elementClass = forName(elementClassName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        // "[Ljava.lang.String;" 风格的数组
        if (name.startsWith("[L") && name.endsWith(";")) {
            String elementName = name.substring("[L".length(), name.length() - 1);
            Class<?> elementClass = forName(elementName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        // "[[I" or "[[Ljava.lang.String;" 风格的数组
        if (name.startsWith("[")) {
            String elementName = name.substring("[".length());
            Class<?> elementClass = forName(elementName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        // 类加载
        ClassLoader clToUse = classLoader;
        if (clToUse == null) {
            clToUse = getDefaultClassLoader();
        }
        try {
            return Class.forName(name, false, clToUse);
        } catch (ClassNotFoundException ex) {
            int lastDotIndex = name.lastIndexOf('.');
            if (lastDotIndex != -1) {
                String innerClassName = name.substring(0, lastDotIndex) + '$' + name.substring(lastDotIndex + 1);
                try {
                    return Class.forName(innerClassName, false, clToUse);
                } catch (ClassNotFoundException ex2) {
                }
            }
            throw ex;
        }


    }

    /**
     * 从 基本数据类型缓存中获取 class
     *
     * @param name
     * @return
     */
    public static Class<?> resolvePrimitiveClassName(String name) {
        Class<?> result = null;
        if (name != null && name.length() <= 8) {
            result = primitiveTypeNameMap.get(name);
        }
        return result;
    }

    /**
     * 类名和指定的类是否匹配
     *
     * @param clazz    指定的类
     * @param typeName 类型字符串
     * @return 是否匹配
     */
    public static boolean matchesTypeName(Class<?> clazz, String typeName) {
        return (typeName != null && (typeName.equals(clazz.getTypeName()) || typeName.equals(clazz.getSimpleName())));
    }

    /**
     * 获取一个 ClassLoader, 优先级如下
     * 1. 当前线程的 ClassLoader
     * 2. 加载当前 ClassUtils 的 ClassLoader
     * 3. 系统的 ClassLoader
     *
     * @return
     */
    public static ClassLoader getDefaultClassLoader() {

        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
        }
        if (cl == null) {

            cl = ClassUtils.class.getClassLoader();
            if (cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                }
            }
        }
        return cl;
    }

    /**
     * 获取指定的 class 中的指定的方法名的个数
     *
     * @param clazz      指定的 class
     * @param methodName 指定的方法名
     * @return
     */
    public static int getMethodCountForName(Class<?> clazz, String methodName) {
        int count = 0;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (methodName.equals(method.getName())) {
                count++;
            }
        }
        Class<?>[] ifcs = clazz.getInterfaces();
        for (Class<?> ifc : ifcs) {
            count += getMethodCountForName(ifc, methodName);
        }
        if (clazz.getSuperclass() != null) {
            count += getMethodCountForName(clazz.getSuperclass(), methodName);
        }
        return count;
    }

    public static String classPackageAsResourcePath(Class<?> clazz) {
        if (clazz == null) {
            return "";
        }
        String className = clazz.getName();
        int packageEndIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
        if (packageEndIndex == -1) {
            return "";
        }
        String packageName = className.substring(0, packageEndIndex);
        return packageName.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR);
    }


    /**
     * 向 commonClassCache 添加新的缓存
     *
     * @param commonClasses
     */
    private static void registerCommonClasses(Class<?>... commonClasses) {
        for (Class<?> clazz : commonClasses) {
            commonClassCache.put(clazz.getName(), clazz);
        }
    }
}
