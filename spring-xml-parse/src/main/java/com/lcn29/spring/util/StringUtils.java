package com.lcn29.spring.util;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <pre>
 * 字符串工具类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 16:09
 */
public class StringUtils {

    /**
     * Collection 转为字符串数组
     *
     * @param collection 需要转换的列表
     * @return
     */
    public static String[] toStringArray(Collection<String> collection) {
        return (collection != null ? collection.toArray(new String[0]) : new String[0]);
    }

    /**
     * 判断字符串长度是否为 0
     *
     * @param str 需要判断的字符串
     * @return true: 不为0, false 为 0
     */
    public static boolean hasLength(String str) {
        return (str != null && !str.isEmpty());
    }

    /**
     * 判断字符串是否有内容，既忽略空格后，字符串的长度大于 0
     *
     * @param str 需要判断的字符串
     * @return true：有内容， false: 没有内容
     */
    public static boolean hasText(String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    /**
     * 字符串内容替换
     *
     * @param inString   替换的字符串
     * @param oldPattern 需要替换的内容
     * @param newPattern 替换后的内容
     * @return 替换后的字符串
     */
    public static String replace(String inString, String oldPattern, String newPattern) {
        if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
            return inString;
        }
        int index = inString.indexOf(oldPattern);
        if (index == -1) {
            // no occurrence -> can return input as-is
            return inString;
        }

        int capacity = inString.length();
        if (newPattern.length() > oldPattern.length()) {
            capacity += 16;
        }
        StringBuilder sb = new StringBuilder(capacity);

        int pos = 0;
        int patLen = oldPattern.length();
        while (index >= 0) {
            sb.append(inString, pos, index);
            sb.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }

        sb.append(inString, pos, inString.length());
        return sb.toString();
    }

    /**
     * 字符串转数组
     *
     * @param str        需要处理的字符串
     * @param delimiters 切换的标志
     * @return 处理后的数组
     */
    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    /**
     * 字符串转数组
     *
     * @param str               需要处理的字符串
     * @param delimiters        切换的标志
     * @param trimTokens        切换处理的内容是否需要去掉前后的空格
     * @param ignoreEmptyTokens 忽略切割符间空的内容
     * @return 处理后的数组
     */
    public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

        if (str == null) {
            return new String[0];
        }

        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }

    /**
     * 判断字符串是否包含非空格的字符
     *
     * @param str 判断的字符串
     * @return true: 包含非空格的内容，false： 只包含或者不包含任何内容
     */
    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
