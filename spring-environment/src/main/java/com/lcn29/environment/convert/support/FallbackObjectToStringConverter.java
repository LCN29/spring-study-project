package com.lcn29.environment.convert.support;

import com.lcn29.environment.convert.TypeDescriptor;
import com.lcn29.environment.convert.converter.ConditionalGenericConverter;

import java.io.StringWriter;
import java.util.Collections;
import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:08
 */
final class FallbackObjectToStringConverter  implements ConditionalGenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Object.class, String.class));
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        Class<?> sourceClass = sourceType.getObjectType();
        if (String.class == sourceClass) {
            // no conversion required
            return false;
        }
        return (CharSequence.class.isAssignableFrom(sourceClass) ||
                StringWriter.class.isAssignableFrom(sourceClass) ||
                ObjectToObjectConverter.hasConversionMethodOrConstructor(sourceClass, String.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return (source != null ? source.toString() : null);
    }

}
