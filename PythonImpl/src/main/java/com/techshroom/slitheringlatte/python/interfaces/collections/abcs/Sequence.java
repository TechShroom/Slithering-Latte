package com.techshroom.slitheringlatte.python.interfaces.collections.abcs;

import java.util.Iterator;

import com.techshroom.slitheringlatte.python.interfaces.Generator;

/**
 * @author Kenzie Togami
 *
 * @param <T>
 *            - The type of the items in this set
 */
public interface Sequence<T> extends PythonSized, Iterable<T>,
        PythonContainer<T> {

    /**
     * Called to implement evaluation of {@code self[key]}. For sequence types,
     * the accepted keys should be integers and slice objects. Note that the
     * special interpretation of negative indexes (if the class wishes to
     * emulate a sequence type) is up to the {@link #getItem(int)} method. If
     * key is of an inappropriate type, TypeError may be raised; if of a value
     * outside the set of indexes for the sequence (after any special
     * interpretation of negative values), IndexError should be raised.
     * 
     * <p>
     * Note: {@code for} loops expect that an IndexError will be raised for
     * illegal indexes to allow proper detection of the end of the sequence.
     * </p>
     * 
     * @param key
     *            - The index
     * @return The item at {@code key}
     */
    T getItem(int key);

    @Override
    default Iterator<T> iterator() {
        return Generator.newGenerator(yielder -> {
            for (int i = 0; i < length(); i++) {
                yielder.yield((T) getItem(i));
            }
        });
    }

}
