package com.techshroom.slitheringlatte.python.interfaces;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.techshroom.slitheringlatte.python.error.AttributeError;

/**
 * Shared space for interfaces to keep their stuff.
 * 
 * @author Kenzie Togami
 */
public final class SharedInterfaceObjectSpace {
    private static final Table<WeakReference<Object>, WeakReference<String>, Object> sharedObjectSpace =
            HashBasedTable.create();
    private static final LoadingCache<Object, WeakReference<?>> referenceCache =
            CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES)
                    .build(new CacheLoader<Object, WeakReference<?>>() {

                        @Override
                        public WeakReference<?> load(Object key)
                                throws Exception {
                            return new WeakReference<Object>(key);
                        }

                    });
    private static final Supplier<?> _NULL_SUPPLIER = () -> null;

    @SuppressWarnings("unchecked")
    private static <T> Supplier<T> nullSupplier() {
        return (Supplier<T>) _NULL_SUPPLIER;
    }

    @SuppressWarnings("unchecked")
    private static <T> WeakReference<T> ref(T instance) {
        return (WeakReference<T>) referenceCache.getUnchecked(instance);
    }

    /**
     * Set a value by instance and key.
     * 
     * @param instance
     *            - The object instance the value "resides" in
     * @param key
     *            - The key that maps to the value
     * @param value
     *            - The value to set
     */
    public static void set(Object instance, String key, Object value) {
        sharedObjectSpace.put(ref(instance), ref(key), value);
    }

    /**
     * Get a value by instance and key.
     * 
     * @param instance
     *            - The object instance the value "resides" in
     * @param key
     *            - The key that maps to the value
     * @return The value
     */
    public static <T> T get(Object instance, String key) {
        return get(instance, key, nullSupplier());
    }

    /**
     * Get a value by instance and key.
     * 
     * @param instance
     *            - The object instance the value "resides" in
     * @param key
     *            - The key that maps to the value
     * @param generator
     *            - The generator for the mapping, called to create
     * @return The value
     */
    public static <T> T get(Object instance, String key, Supplier<T> generator) {
        WeakReference<Object> instRef = ref(instance);
        WeakReference<String> keyRef = ref(key);
        if (!sharedObjectSpace.contains(instRef, keyRef)) {
            if (generator == _NULL_SUPPLIER) {
                throw new AttributeError(instance.getClass().getName() + " '"
                        + instance + "' has no attribute " + key);
            } else {
                sharedObjectSpace
                        .put(instRef, keyRef, (Object) generator.get());
            }
        }
        // so that it doesn't show up in javadoc
        @SuppressWarnings("unchecked")
        T obj = (T) sharedObjectSpace.get(instRef, keyRef);
        return obj;
    }
}
