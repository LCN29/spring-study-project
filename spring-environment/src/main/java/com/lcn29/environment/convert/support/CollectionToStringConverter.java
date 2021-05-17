package com.lcn29.environment.convert.support;

import com.lcn29.environment.convert.ConversionService;
import com.lcn29.environment.convert.TypeDescriptor;
import com.lcn29.environment.convert.converter.ConditionalGenericConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:37
 */
final class CollectionToStringConverter implements ConditionalGenericConverter {

    private static final String DELIMITER = ",";

    private final ConversionService conversionService;


    public CollectionToStringConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Collection.class, String.class));
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return ConversionUtils.canConvertElements(
                sourceType.getElementTypeDescriptor(), targetType, this.conversionService);
    }

    @Override
    public Object convert( Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        Collection<?> sourceCollection = (Collection<?>) source;
        if (sourceCollection.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Object sourceElement : sourceCollection) {
            if (i > 0) {
                sb.append(DELIMITER);
            }
            Object targetElement = this.conversionService.convert(
                    sourceElement, sourceType.elementTypeDescriptor(sourceElement), targetType);
            sb.append(targetElement);
            i++;
        }
        return sb.toString();
    }

}
