package com.lcn29.convert.support;

import com.lcn29.convert.ConversionService;
import com.lcn29.convert.TypeDescriptor;
import com.lcn29.convert.converter.ConditionalGenericConverter;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:23
 */
final class CollectionToArrayConverter implements ConditionalGenericConverter {

    private final ConversionService conversionService;


    public CollectionToArrayConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Collection.class, Object[].class));
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return ConversionUtils.canConvertElements(sourceType.getElementTypeDescriptor(),
                targetType.getElementTypeDescriptor(), this.conversionService);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        Collection<?> sourceCollection = (Collection<?>) source;
        TypeDescriptor targetElementType = targetType.getElementTypeDescriptor();
        Object array = Array.newInstance(targetElementType.getType(), sourceCollection.size());
        int i = 0;
        for (Object sourceElement : sourceCollection) {
            Object targetElement = this.conversionService.convert(sourceElement,
                    sourceType.elementTypeDescriptor(sourceElement), targetElementType);
            Array.set(array, i++, targetElement);
        }
        return array;
    }
}
