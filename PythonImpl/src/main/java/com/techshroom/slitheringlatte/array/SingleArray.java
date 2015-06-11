package com.techshroom.slitheringlatte.array;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Array;

/**
 * Single object array creation, simplified.
 * 
 * @author Kenzie Togami
 */
public final class SingleArray {

    private SingleArray() {
        throw new AssertionError();
    }

    /**
     * Function for <code>new T[] { object }</code>.
     * 
     * @param object
     *            - object to wrap
     * @return an array of size 1 containing the object
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] of(T object) {
        checkNotNull(object,
                "You must specifiy the class if the object is null");
        return of(object, (Class<T>) object.getClass());
    }

    /**
     * Function for <code>new T[] { object }</code>.
     * 
     * @param object
     *            - object to wrap
     * @param arrayType
     *            - the array type, mainly for forcing it with a null object
     * @return an array of size 1 containing the object
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] of(T object, Class<T> arrayType) {
        T[] array = (T[]) Array.newInstance(arrayType, 1);
        array[0] = object;
        return array;
    }
}
