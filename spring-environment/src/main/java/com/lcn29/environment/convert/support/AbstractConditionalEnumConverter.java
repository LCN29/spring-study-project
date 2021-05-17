package com.lcn29.environment.convert.support;

import com.lcn29.environment.convert.ConversionService;
import com.lcn29.environment.convert.TypeDescriptor;
import com.lcn29.environment.convert.converter.ConditionalConverter;
import com.lcn29.environment.util.ClassUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:48
 */
abstract class AbstractConditionalEnumConverter implements ConditionalConverter {

    private final ConversionService conversionService;


    protected AbstractConditionalEnumConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        for (Class<?> interfaceType : ClassUtils.getAllInterfacesForClassAsSet(sourceType.getType())) {
            if (this.conversionService.canConvert(TypeDescriptor.valueOf(interfaceType), targetType)) {
                return false;
            }
        }
        return true;
    }
}
