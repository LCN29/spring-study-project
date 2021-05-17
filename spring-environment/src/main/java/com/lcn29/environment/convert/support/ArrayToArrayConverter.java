package com.lcn29.environment.convert.support;

import com.lcn29.environment.convert.ConversionService;
import com.lcn29.environment.convert.TypeDescriptor;
import com.lcn29.environment.convert.converter.ConditionalGenericConverter;
import com.lcn29.environment.util.ObjectUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:24
 */
final class ArrayToArrayConverter implements ConditionalGenericConverter {

    private final CollectionToArrayConverter helperConverter;

    private final ConversionService conversionService;


    public ArrayToArrayConverter(ConversionService conversionService) {
        this.helperConverter = new CollectionToArrayConverter(conversionService);
        this.conversionService = conversionService;
    }


    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Object[].class, Object[].class));
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return this.helperConverter.matches(sourceType, targetType);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (this.conversionService instanceof GenericConversionService) {
            TypeDescriptor targetElement = targetType.getElementTypeDescriptor();
            if (targetElement != null &&
                    ((GenericConversionService) this.conversionService).canBypassConvert(
                            sourceType.getElementTypeDescriptor(), targetElement)) {
                return source;
            }
        }
        List<Object> sourceList = Arrays.asList(ObjectUtils.toObjectArray(source));
        return this.helperConverter.convert(sourceList, sourceType, targetType);
    }

}
