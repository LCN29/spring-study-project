package com.lcn29.environment.convert.support;

import com.lcn29.environment.convert.ConversionService;
import com.lcn29.environment.convert.TypeDescriptor;
import com.lcn29.environment.convert.converter.GenericConverter;
import com.lcn29.environment.exception.ConversionFailedException;
import com.lcn29.environment.util.ClassUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 21:43
 */
public abstract class ConversionUtils {

    public static Object invokeConverter(GenericConverter converter, Object source,
                                         TypeDescriptor sourceType, TypeDescriptor targetType) {
        try {
            return converter.convert(source, sourceType, targetType);
        } catch (ConversionFailedException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new ConversionFailedException(sourceType, targetType, source, ex);
        }
    }

    public static boolean canConvertElements(TypeDescriptor sourceElementType, TypeDescriptor targetElementType, ConversionService conversionService) {


        if (targetElementType == null) {
            return true;
        }
        if (sourceElementType == null) {
            return true;
        }
        if (conversionService.canConvert(sourceElementType, targetElementType)) {
            return true;
        }
        if (ClassUtils.isAssignable(sourceElementType.getType(), targetElementType.getType())) {
            return true;
        }
        return false;
    }

    public static Class<?> getEnumType(Class<?> targetType) {
        Class<?> enumType = targetType;
        while (enumType != null && !enumType.isEnum()) {
            enumType = enumType.getSuperclass();
        }
        return enumType;
    }

}
