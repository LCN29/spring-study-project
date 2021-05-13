package com.lcn29.convert.converter;

import com.lcn29.convert.TypeDescriptor;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-13  18:07
 */
public interface ConditionalConverter {

	boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType);
}
