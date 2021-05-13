package com.lcn29.convert.support;

import com.lcn29.convert.ConversionService;
import com.lcn29.convert.TypeDescriptor;
import com.lcn29.convert.converter.ConditionalGenericConverter;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:09
 */
public class ObjectToOptionalConverter implements ConditionalGenericConverter {

    private final ConversionService conversionService;


    public ObjectToOptionalConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> convertibleTypes = new LinkedHashSet<>(4);
        convertibleTypes.add(new ConvertiblePair(Collection.class, Optional.class));
        convertibleTypes.add(new ConvertiblePair(Object[].class, Optional.class));
        convertibleTypes.add(new ConvertiblePair(Object.class, Optional.class));
        return convertibleTypes;
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (targetType.getResolvableType().hasGenerics()) {
            return this.conversionService.canConvert(sourceType, new GenericTypeDescriptor(targetType));
        }
        else {
            return true;
        }
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return Optional.empty();
        }
        else if (source instanceof Optional) {
            return source;
        }
        else if (targetType.getResolvableType().hasGenerics()) {
            Object target = this.conversionService.convert(source, sourceType, new GenericTypeDescriptor(targetType));
            if (target == null || (target.getClass().isArray() && Array.getLength(target) == 0) ||
                    (target instanceof Collection && ((Collection<?>) target).isEmpty())) {
                return Optional.empty();
            }
            return Optional.of(target);
        }
        else {
            return Optional.of(source);
        }
    }

    @SuppressWarnings("serial")
    private static class GenericTypeDescriptor extends TypeDescriptor {

        public GenericTypeDescriptor(TypeDescriptor typeDescriptor) {
            super(typeDescriptor.getResolvableType().getGeneric(), null, typeDescriptor.getAnnotations());
        }
    }
}
