package com.lcn29.spring.reader;

import com.lcn29.spring.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <pre>
 * XML 的校验模式, 涉及到 xml 文件的校验, 可以查看 dtd 和 xsd 的区别
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-06 14:08
 */
public class XmlValidationModeDetector {

    /**
     * 不校验
     */
    public static final int VALIDATION_NONE = 0;

    /**
     * 自动检测 xml 文件, 是启用 3 还是 4
     */
    public static final int VALIDATION_AUTO = 1;

    /**
     * dtd 的校验模式
     */
    public static final int VALIDATION_DTD = 2;

    /**
     * xsd 的校验模式
     */
    public static final int VALIDATION_XSD = 3;


    private static final String DOCTYPE = "DOCTYPE";
    private static final String START_COMMENT = "<!--";
    private static final String END_COMMENT = "-->";

    /**
     * 当前在解析的位置是否在 xml 的注释内容中
     */
    private boolean inComment;

    public int detectValidationMode(InputStream inputStream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            boolean isDtdValidated = false;
            String content;

            while ((content = reader.readLine()) != null) {
                content = consumeCommentTokens(content);
                if (this.inComment || !StringUtils.hasText(content)) {
                    continue;
                }
                if (hasDoctype(content)) {
                    isDtdValidated = true;
                    break;
                }
                if (hasOpeningTag(content)) {
                    break;
                }
            }
            return (isDtdValidated ? VALIDATION_DTD : VALIDATION_XSD);
        } catch (Exception e) {

            return VALIDATION_AUTO;
        } finally {
            reader.close();
        }
    }

    private String consumeCommentTokens(String line) {
        int indexOfStartComment = line.indexOf(START_COMMENT);
        if (indexOfStartComment == -1 && !line.contains(END_COMMENT)) {
            return line;
        }

        String result = "";
        String currLine = line;
        if (indexOfStartComment >= 0) {
            result = line.substring(0, indexOfStartComment);
            currLine = line.substring(indexOfStartComment);
        }

        while ((currLine = consume(currLine)) != null) {
            if (!this.inComment && !currLine.trim().startsWith(START_COMMENT)) {
                return result + currLine;
            }
        }
        return null;
    }

    private String consume(String line) {
        int index = (this.inComment ? endComment(line) : startComment(line));
        return (index == -1 ? null : line.substring(index));
    }

    private int startComment(String line) {
        return commentToken(line, START_COMMENT, true);
    }

    private int endComment(String line) {
        return commentToken(line, END_COMMENT, false);
    }

    private int commentToken(String line, String token, boolean inCommentIfPresent) {
        int index = line.indexOf(token);
        if (index > -1) {
            this.inComment = inCommentIfPresent;
        }
        return (index == -1 ? index : index + token.length());
    }

    private boolean hasDoctype(String content) {
        return content.contains(DOCTYPE);
    }

    private boolean hasOpeningTag(String content) {
        if (this.inComment) {
            return false;
        }
        int openTagIndex = content.indexOf('<');
        return (openTagIndex > -1 && (content.length() > openTagIndex + 1) && Character.isLetter(content.charAt(openTagIndex + 1)));
    }
}
