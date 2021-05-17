package com.lcn29.environment.exception;

import com.lcn29.environment.convert.TypeDescriptor;
import com.lcn29.environment.util.ObjectUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 21:44
 */
public class ConversionFailedException extends RuntimeException{

    private final TypeDescriptor sourceType;

    private final TypeDescriptor targetType;

    private final Object value;

    public ConversionFailedException(TypeDescriptor sourceType, TypeDescriptor targetType, Object value, Throwable cause) {

        super("Failed to convert from type [" + sourceType + "] to type [" + targetType + "] for value '" + ObjectUtils.nullSafeToString(value) + "'", cause);
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.value = value;
    }

    public TypeDescriptor getSourceType() {
        return this.sourceType;
    }

    public TypeDescriptor getTargetType() {
        return this.targetType;
    }

    public Object getValue() {
        return this.value;
    }

}
