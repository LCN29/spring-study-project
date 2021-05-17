package com.lcn29.environment.core;

import com.lcn29.environment.util.MultiValueMap;
import com.lcn29.environment.util.ReflectionUtils;

import java.util.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-13 22:14
 */
public final class CollectionFactory {

    private static final Set<Class<?>> approximableCollectionTypes = new HashSet<>();

    private static final Set<Class<?>> approximableMapTypes = new HashSet<>();

    static {
        // Standard collection interfaces
        approximableCollectionTypes.add(Collection.class);
        approximableCollectionTypes.add(List.class);
        approximableCollectionTypes.add(Set.class);
        approximableCollectionTypes.add(SortedSet.class);
        approximableCollectionTypes.add(NavigableSet.class);
        approximableMapTypes.add(Map.class);
        approximableMapTypes.add(SortedMap.class);
        approximableMapTypes.add(NavigableMap.class);

        // Common concrete collection classes
        approximableCollectionTypes.add(ArrayList.class);
        approximableCollectionTypes.add(LinkedList.class);
        approximableCollectionTypes.add(HashSet.class);
        approximableCollectionTypes.add(LinkedHashSet.class);
        approximableCollectionTypes.add(TreeSet.class);
        approximableCollectionTypes.add(EnumSet.class);
        approximableMapTypes.add(HashMap.class);
        approximableMapTypes.add(LinkedHashMap.class);
        approximableMapTypes.add(TreeMap.class);
        approximableMapTypes.add(EnumMap.class);
    }

    private CollectionFactory() {
    }

    public static boolean isApproximableCollectionType(Class<?> collectionType) {
        return (collectionType != null && approximableCollectionTypes.contains(collectionType));
    }

    public static <E> Collection<E> createCollection(Class<?> collectionType, Class<?> elementType, int capacity) {
        if (collectionType.isInterface()) {
            if (Set.class == collectionType || Collection.class == collectionType) {
                return new LinkedHashSet<>(capacity);
            } else if (List.class == collectionType) {
                return new ArrayList<>(capacity);
            } else if (SortedSet.class == collectionType || NavigableSet.class == collectionType) {
                return new TreeSet<>();
            } else {
                throw new IllegalArgumentException("Unsupported Collection interface: " + collectionType.getName());
            }
        } else if (EnumSet.class.isAssignableFrom(collectionType)) {
            return (Collection<E>) EnumSet.noneOf(asEnumType(elementType));
        } else {
            if (!Collection.class.isAssignableFrom(collectionType)) {
                throw new IllegalArgumentException("Unsupported Collection type: " + collectionType.getName());
            }
            try {
                return (Collection<E>) ReflectionUtils.accessibleConstructor(collectionType).newInstance();
            } catch (Throwable ex) {
                throw new IllegalArgumentException("Could not instantiate Collection type: " + collectionType.getName(), ex);
            }
        }
    }

    public static <K, V> Map<K, V> createMap(Class<?> mapType,Class<?> keyType, int capacity) {
        if (mapType.isInterface()) {
            if (Map.class == mapType) {
                return new LinkedHashMap<>(capacity);
            } else if (SortedMap.class == mapType || NavigableMap.class == mapType) {
                return new TreeMap<>();
            } else if (MultiValueMap.class == mapType) {
                return new LinkedMultiValueMap();
            } else {
                throw new IllegalArgumentException("Unsupported Map interface: " + mapType.getName());
            }
        } else if (EnumMap.class == mapType) {
            return new EnumMap(asEnumType(keyType));
        } else {
            if (!Map.class.isAssignableFrom(mapType)) {
                throw new IllegalArgumentException("Unsupported Map type: " + mapType.getName());
            }
            try {
                return (Map<K, V>) ReflectionUtils.accessibleConstructor(mapType).newInstance();
            } catch (Throwable ex) {
                throw new IllegalArgumentException("Could not instantiate Map type: " + mapType.getName(), ex);
            }
        }
    }


    private static Class<? extends Enum> asEnumType(Class<?> enumType) {
        if (!Enum.class.isAssignableFrom(enumType)) {
            throw new IllegalArgumentException("Supplied type is not an enum: " + enumType.getName());
        }
        return enumType.asSubclass(Enum.class);
    }

}
