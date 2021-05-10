package com.lcn29.core;

import com.lcn29.util.ConcurrentReferenceHashMap;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author
 * @date 2021-05-10  18:09
 */
public class ResolvableType implements Serializable {

	public static final ResolvableType NONE = new ResolvableType(EmptyType.INSTANCE, null, null, 0);

	private static final ResolvableType[] EMPTY_TYPES_ARRAY = new ResolvableType[0];

	private static final ConcurrentReferenceHashMap<ResolvableType, ResolvableType> cache = new ConcurrentReferenceHashMap<>(256);

	private final Type type;

	private final TypeProvider typeProvider;

	private final VariableResolver variableResolver;

	private final ResolvableType componentType;

	private final Integer hash;

	private Class<?> resolved;

	private volatile ResolvableType superType;

	private volatile ResolvableType[] interfaces;

	private volatile ResolvableType[] generics;

	private ResolvableType(Type type, TypeProvider typeProvider, VariableResolver variableResolver) {

		this.type = type;
		this.typeProvider = typeProvider;
		this.variableResolver = variableResolver;
		this.componentType = null;
		this.hash = calculateHashCode();
		this.resolved = null;

	}

	private ResolvableType(Type type, TypeProvider typeProvider, VariableResolver variableResolver, Integer hash) {

		this.type = type;
		this.typeProvider = typeProvider;
		this.variableResolver = variableResolver;
		this.componentType = null;
		this.hash = hash;
		this.resolved = resolveClass();
	}

	private ResolvableType(Type type, TypeProvider typeProvider, VariableResolver variableResolver, ResolvableType componentType) {

		this.type = type;
		this.typeProvider = typeProvider;
		this.variableResolver = variableResolver;
		this.componentType = componentType;
		this.hash = null;
		this.resolved = resolveClass();
	}

	private ResolvableType(Class<?> clazz) {
		this.resolved = (clazz != null ? clazz : Object.class);
		this.type = this.resolved;
		this.typeProvider = null;
		this.variableResolver = null;
		this.componentType = null;
		this.hash = null;
	}

	public Type getType() {
		return SerializableTypeWrapper.unwrap(this.type);
	}

	public Class<?> getRawClass() {
		if (this.type == this.resolved) {
			return this.resolved;
		}
		Type rawType = this.type;
		if (rawType instanceof ParameterizedType) {
			rawType = ((ParameterizedType) rawType).getRawType();
		}
		return (rawType instanceof Class ? (Class<?>) rawType : null);
	}

	public Object getSource() {
		Object source = (this.typeProvider != null ? this.typeProvider.getSource() : null);
		return (source != null ? source : this.type);
	}

	public Class<?> toClass() {
		return resolve(Object.class);
	}

	public boolean isInstance( Object obj) {
		return (obj != null && isAssignableFrom(obj.getClass()));
	}

	public boolean isAssignableFrom(Class<?> other) {
		return isAssignableFrom(forClass(other), null);
	}

	public boolean isAssignableFrom(ResolvableType other) {
		return isAssignableFrom(other, null);
	}

	private boolean isAssignableFrom(ResolvableType other,  Map<Type, Type> matchedBefore) {

		if (this == NONE || other == NONE) {
			return false;
		}

		if (isArray()) {
			return (other.isArray() && getComponentType().isAssignableFrom(other.getComponentType()));
		}

		if (matchedBefore != null && matchedBefore.get(this.type) == other.type) {
			return true;
		}

		WildcardBounds ourBounds = WildcardBounds.get(this);
		WildcardBounds typeBounds = WildcardBounds.get(other);

		if (typeBounds != null) {
			return (ourBounds != null && ourBounds.isSameKind(typeBounds) && ourBounds.isAssignableFrom(typeBounds.getBounds()));
		}

		if (ourBounds != null) {
			return ourBounds.isAssignableFrom(other);
		}
	}

	static class EmptyType implements Type, Serializable {

		static final Type INSTANCE = new EmptyType();

		Object readResolve() {
			return INSTANCE;
		}
	}
}
