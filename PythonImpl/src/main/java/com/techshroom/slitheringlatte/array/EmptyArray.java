package com.techshroom.slitheringlatte.array;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Empty array storage. Preferred to storing a
 * {@code private static final T[] T_EMPTY_ARRAY} because the array can be
 * shared across classes (provided the cache is not filling up) and are easily
 * passed around with resorting to weird generics.
 * 
 * @author Kenzie Togami
 * @param <T>
 *            - type for the array
 */
public final class EmptyArray<T> {
    private static final LoadingCache<Class<?>, EmptyArray<?>> cache =
            CacheBuilder.newBuilder().concurrencyLevel(1).maximumSize(100)
                    .removalListener(notify -> {
                    }).build(new CacheLoader<Class<?>, EmptyArray<?>>() {
                        @Override
                        public EmptyArray<?> load(Class<?> key) {
                            return new EmptyArray<>(key);
                        }

                        @Override
                        public Map<Class<?>, EmptyArray<?>> loadAll(
                                Iterable<? extends Class<?>> keys) {
                            Collector<Class<?>, ?, Map<Class<?>, EmptyArray<?>>> toMap =
                                    Collectors.toMap(Function.identity(),
                                                     this::load);
                            return StreamSupport.stream(keys.spliterator(),
                                                        false).collect(toMap);
                        }
                    });
    /**
     * String shortcut.
     */
    public static final EmptyArray<String> STRING = of(String.class);

    /**
     * Fetch the empty array holder for the given class.
     * 
     * @param clazz
     *            - bound class
     * @return the empty array container for the given class
     */
    @SuppressWarnings("unchecked")
    public static <T> EmptyArray<T> of(Class<T> clazz) {
        return (EmptyArray<T>) cache.getUnchecked(clazz);
    }

    private final Class<T> arrayType;
    private final Object array;

    private EmptyArray(Class<T> type) {
        arrayType = type;
        array = Array.newInstance(arrayType, 0);
    }

    /**
     * Get the empty array instance.
     * 
     * @return an empty array of type T.
     */
    @SuppressWarnings("unchecked")
    public T[] get() {
        return (T[]) array;
    }

    /**
     * Returns the same as {@link #get()}, except un-cast so that primitive
     * arrays are allowed.
     * 
     * @return
     */
    public Object getUnsafe() {
        return array;
    }
}
