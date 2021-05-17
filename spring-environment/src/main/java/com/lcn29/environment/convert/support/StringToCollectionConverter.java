package com.lcn29.environment.convert.support;

import com.lcn29.environment.convert.ConversionService;
import com.lcn29.environment.convert.TypeDescriptor;
import com.lcn29.environment.convert.converter.ConditionalGenericConverter;
import com.lcn29.environment.core.CollectionFactory;
import com.lcn29.environment.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:39
 */
final class StringToCollectionConverter implements ConditionalGenericConverter {

    private final ConversionService conversionService;


    public StringToCollectionConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Collection.class));
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return (targetType.getElementTypeDescriptor() == null ||
                this.conversionService.canConvert(sourceType, targetType.getElementTypeDescriptor()));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        String string = (String) source;

        String[] fields = StringUtils.commaDelimitedListToStringArray(string);
        TypeDescriptor elementDesc = targetType.getElementTypeDescriptor();
        Collection<Object> target = CollectionFactory.createCollection(targetType.getType(),
                (elementDesc != null ? elementDesc.getType() : null), fields.length);

        if (elementDesc == null) {
            for (String field : fields) {
                target.add(field.trim());
            }
        } else {
            for (String field : fields) {
                Object targetElement = this.conversionService.convert(field.trim(), sourceType, elementDesc);
                target.add(targetElement);
            }
        }
        return target;
    }

}

