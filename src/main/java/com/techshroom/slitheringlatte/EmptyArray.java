package com.techshroom.slitheringlatte;

import java.lang.reflect.Array;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Empty array storage.
 * 
 * @author Kenzie Togami
 */
public final class EmptyArray {
    private EmptyArray() {
        throw new AssertionError();
    }

    /**
     * String empty array, one of the more used ones so we avoid a lookup.
     */
    public static final String[] STRING = {};

    private static final Map<Class<?>, Object> ARRAYS = Maps.newConcurrentMap();
    static {
        // cause why not
        ARRAYS.put(String.class, STRING);
    }

    /**
     * Generic empty array sharing, for performance reasons.
     * 
     * @param forceT
     *            - force type parameter with this class
     * 
     * @return an empty array of type T
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] get(Class<T> forceT) {
        if (forceT == String.class) {
            return (T[]) STRING;
        }
        if (!ARRAYS.containsKey(forceT)) {
            ARRAYS.put(forceT, Array.newInstance(forceT, 0));
        }
        return (T[]) ARRAYS.get(forceT);
    }
}
