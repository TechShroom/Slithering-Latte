package com.techshroom.slitheringlatte.python.underscore;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Table;
import com.techshroom.slitheringlatte.python.error.TypeError;

/**
 * Really complicated way of implementing LenSupported for Java types. If you
 * can, use LenSupported!
 * 
 * @author Kenzie Togami
 */
public final class LenGetters {
    private static final ToIntFunction<Object> THROW = x -> {
        throw new TypeError(String.format("object of type '%s' has no len()", x
                .getClass().getName()));
    };
    private static final Multimap<Class<?>, Function<Object, Optional<ToIntFunction<Object>>>> getters =
            Multimaps.synchronizedMultimap(MultimapBuilder.hashKeys()
                    .arrayListValues(2).build());

    private static <T> Function<T, Optional<ToIntFunction<T>>> returnGiven(
            ToIntFunction<T> given) {
        Optional<ToIntFunction<T>> opt = Optional.of(given);
        return __ -> opt;
    }

    private static <T> Optional<ToIntFunction<T>> reflectionLength(T t) {
        Method[] methods = t.getClass().getMethods();
        for (Method m : methods) {
            String lc = m.getName().toLowerCase();
            if (lc.equals("length") || lc.equals("size")
                    || lc.equalsIgnoreCase("len")) {
                // some standard length methods
                return Optional.of(x -> {
                    try {
                        return ((Integer) m.invoke(x)).intValue();
                    } catch (Exception e) {
                        return THROW.applyAsInt(x);
                    }
                });
            }
        }
        return Optional.of(x -> THROW.applyAsInt(x));
    }

    static {
        // register all default getters
        registerNewGetterWrap(Collection.class, coll -> coll.size());
        registerNewGetterWrap(CharSequence.class, seq -> seq.length());
        registerNewGetterWrap(Map.class, map -> map.size());
        registerNewGetterWrap(Multimap.class, mmap -> mmap.size());
        registerNewGetterWrap(Table.class, table -> table.size());
        // array length
        registerNewGetter(Object.class,
                          x -> x.getClass().isArray() ? Optional
                                  .of(Array::getLength) : Optional.empty());
        // totally a good idea
        // reflection checker
        registerNewGetter(Object.class, LenGetters::reflectionLength);
    }

    /**
     * Register a new length getter for non-Python types.
     * 
     * @param type
     *            - the type to match for the input
     * @param getter
     *            - the getter that can be applied to the type, which may return
     *            {@link Optional#empty()} to be skipped
     */
    @SuppressWarnings("unchecked")
    public static <T> void registerNewGetter(Class<T> type,
            Function<T, Optional<ToIntFunction<T>>> getter) {
        checkNotNull(type);
        checkNotNull(getter);
        // weird thing to convert to ? generic
        synchronized (getters) {
            getters.put(type, o -> {
                Optional<ToIntFunction<T>> generic = getter.apply((T) o);
                if (generic.isPresent()) {
                    return Optional.of(x -> generic.get().applyAsInt((T) x));
                } else {
                    return Optional.empty();
                }
            });
        }
    }

    /**
     * Register a new length getter for non-Python types.
     * 
     * @param type
     *            - the type to match for the input
     * @param getter
     *            - the getter that can be applied to the type
     */
    public static <T> void registerNewGetterWrap(Class<T> type,
            ToIntFunction<T> getter) {
        registerNewGetter(type, returnGiven(getter));
    }

    /**
     * Get the getter for the given object. If there is none, a function that
     * throws a TypeError is returned.
     * 
     * @param o
     *            - object to get a getter for
     * @return the getter if there is one
     */
    public static ToIntFunction<Object> getterFor(Object o) {
        Function<Class<?>, ToIntFunction<Object>> search =
                clazz -> {
                    Collection<Function<Object, Optional<ToIntFunction<Object>>>> test =
                            getters.get(Void.class);
                    for (Function<Object, Optional<ToIntFunction<Object>>> function : test) {
                        Optional<ToIntFunction<Object>> applyThis =
                                function.apply(o);
                        if (applyThis.isPresent()) {
                            return applyThis.get();
                        }
                    }
                    return null;
                };
        synchronized (getters) {
            if (o == null) {
                return search.andThen(x -> x == null ? THROW : x)
                        .apply(Void.class);
            } else {
                Class<? extends Object> base = o.getClass();
                do {
                    ToIntFunction<Object> func = search.apply(base);
                    if (func != null) {
                        return func;
                    }
                    if (base.isArray()) {
                        throw new IllegalStateException(
                                "Array should have length");
                    }
                    base = base.getSuperclass();
                } while (base != null);
            }
            return THROW;
        }
    }

    private LenGetters() {
        throw new AssertionError("Nope.");
    }
}
