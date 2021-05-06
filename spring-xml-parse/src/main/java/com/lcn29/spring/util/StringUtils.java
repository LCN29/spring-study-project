package com.lcn29.spring.util;


import java.util.*;

/**
 * <pre>
 * 字符串工具类
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-03 16:09
 */
public class StringUtils {

    private static final String FOLDER_SEPARATOR = "/";

    private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

    private static final String TOP_PATH = "..";

    private static final String CURRENT_PATH = ".";

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
     * 文件格式格式化
     *
     * @param path
     * @return
     */
    public static String cleanPath(String path) {
        if (!hasLength(path)) {
            return path;
        }
        String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);

        int prefixIndex = pathToUse.indexOf(':');
        String prefix = "";

        if (prefixIndex != -1) {
            prefix = pathToUse.substring(0, prefixIndex + 1);

            if (prefix.contains(FOLDER_SEPARATOR)) {
                prefix = "";
            } else {
                pathToUse = pathToUse.substring(prefixIndex + 1);
            }
        }

        if (pathToUse.startsWith(FOLDER_SEPARATOR)) {
            prefix = prefix + FOLDER_SEPARATOR;
            pathToUse = pathToUse.substring(1);
        }

        String[] pathArray = delimitedListToStringArray(pathToUse, FOLDER_SEPARATOR);
        LinkedList<String> pathElements = new LinkedList<>();
        int tops = 0;

        for (int i = pathArray.length - 1; i >= 0; i--) {
            String element = pathArray[i];
            if (CURRENT_PATH.equals(element)) {
            } else if (TOP_PATH.equals(element)) {
                tops++;
            } else {
                if (tops > 0) {
                    tops--;
                } else {
                    pathElements.addFirst(element);
                }
            }
        }

        for (int i = 0; i < tops; i++) {
            pathElements.addFirst(TOP_PATH);
        }

        if (pathElements.size() == 1 && pathElements.getLast().isEmpty() && !prefix.endsWith(FOLDER_SEPARATOR)) {
            pathElements.addFirst(CURRENT_PATH);
        }

        return prefix + collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
    }

    /**
     * string  分割为 数组
     *
     * @param str
     * @param delimiter
     * @return
     */
    public static String[] delimitedListToStringArray(String str, String delimiter) {
        return delimitedListToStringArray(str, delimiter, null);
    }

    /**
     * string  分割为 数组
     *
     * @param str           字符串
     * @param delimiter     分割字符
     * @param charsToDelete 需要删除的字符
     * @return
     */
    public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {
        if (str == null) {
            return new String[0];
        }
        if (delimiter == null) {
            return new String[]{str};
        }

        List<String> result = new ArrayList<>();
        if (delimiter.isEmpty()) {
            for (int i = 0; i < str.length(); i++) {
                result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
            }
        } else {
            int pos = 0;
            int delPos;

            while ((delPos = str.indexOf(delimiter, pos)) != -1) {
                result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
                pos = delPos + delimiter.length();
            }

            if (str.length() > 0 && pos <= str.length()) {
                result.add(deleteAny(str.substring(pos), charsToDelete));
            }
        }
        return toStringArray(result);
    }

    /**
     * 删除字符串中指定的字符
     *
     * @param inString
     * @param charsToDelete
     * @return
     */
    public static String deleteAny(String inString, String charsToDelete) {
        if (!hasLength(inString) || !hasLength(charsToDelete)) {
            return inString;
        }

        StringBuilder sb = new StringBuilder(inString.length());
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            if (charsToDelete.indexOf(c) == -1) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 集合拼接为字符串
     *
     * @param coll
     * @param delim
     * @return
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    /**
     * 集合拼接为字符串
     *
     * @param coll
     * @param delim
     * @return
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim, String prefix, String suffix) {

        if (CollectionUtils.isEmpty(coll)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = coll.iterator();
        while (it.hasNext()) {
            sb.append(prefix).append(it.next()).append(suffix);
            if (it.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    /**
     * 根据路径获取文件名
     *
     * @param path
     * @return
     */
    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }

        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
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
