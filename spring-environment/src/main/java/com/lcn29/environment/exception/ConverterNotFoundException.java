package com.lcn29.environment.exception;

import com.lcn29.environment.convert.TypeDescriptor;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 21:46
 */
public class ConverterNotFoundException extends RuntimeException {

    private final TypeDescriptor sourceType;

    private final TypeDescriptor targetType;

    public ConverterNotFoundException(TypeDescriptor sourceType, TypeDescriptor targetType) {
        super("No converter found capable of converting from type [" + sourceType + "] to type [" + targetType + "]");
        this.sourceType = sourceType;
        this.targetType = targetType;
    }

    public TypeDescriptor getSourceType() {
        return this.sourceType;
    }

    public TypeDescriptor getTargetType() {
        return this.targetType;
    }

}
