package com.techshroom.slitheringlatte.python.interfaces.collections.abcs;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import com.techshroom.slitheringlatte.python.error.IndexError;
import com.techshroom.slitheringlatte.python.error.ValueError;
import com.techshroom.slitheringlatte.python.interfaces.Reversible;
import com.techshroom.slitheringlatte.python.interfaces.generated.OperatorIAdd;

/**
 * @author Kenzie Togami
 *
 * @param <T>
 *            - The type of the items in this set
 */
public interface PythonSequence<T> extends PythonSized, Iterable<T>,
        PythonContainer<T>, Reversible<T> {

    interface Mutable<T> extends PythonSequence<T>, OperatorIAdd {

        /**
         * Called to implement assignment to {@code self[key]}. Same note as for
         * {@link #getItem(int)}. This should only be implemented for mappings
         * if the objects support changes to the values for keys, or if new keys
         * can be added, or for sequences if elements can be replaced. The same
         * exceptions should be raised for improper key values as for the
         * {@link #getItem(int)} method.
         * 
         * @param index
         *            - The index to put item at
         * @param item
         *            - The item to put at index
         */
        void setItem(int index, T item);

        /**
         * Inserts {@code item} before {@code index}.
         * 
         * @param index
         *            - The index to put {@code item} before
         * @param item
         *            - The item to put before {@code index}
         */
        void insert(int index, T item);

        /**
         * Called to implement deletion of {@code self[key]}. Same note as for
         * {@link #getItem(int)}. This should only be implemented for mappings
         * if the objects support removal of keys, or for sequences if elements
         * can be removed from the sequence. The same exceptions should be
         * raised for improper key values as for the {@link #getItem(int)}
         * method.
         * 
         * @param index
         *            - The index of the item to delete
         */
        void delItem(int index);

        /**
         * Appends {@code item} to the end of the sequence.
         * 
         * @param item
         *            - The item to append
         */
        default void append(T item) {
            insert(length(), item);
        }

        /**
         * Removes all items.
         */
        default void clear() {
            while (length() > 0) {
                pop();
            }
        }

        /**
         * Reverses this sequence in-place.
         */
        default void reverse() {
            int len = length();
            int lo2 = len / 2;
            for (int i = 0; i < lo2; i++) {
                int idx = len - i - 1;
                T tmp = getItem(i);
                setItem(i, getItem(idx));
                setItem(idx, tmp);
            }
        }

        /**
         * Extends this sequence by appending elements from the iterable.
         * 
         * @param values
         *            - The elements to append
         */
        default void extend(Iterable<T> values) {
            Iterator<T> it = values.iterator();
            while (it.hasNext()) {
                T type = it.next();
                append(type);
            }
        }

        /**
         * Removes and returns the item at the end of the sequence.
         * 
         * @return The popped item
         * @throws IndexError
         *             if list is empty
         */
        default T pop() {
            return pop(-1);
        }

        /**
         * Removes and returns the item at {@code index}.
         * 
         * @param index
         *            - The index of the item to pop
         * @return The popped item
         * @throws IndexError
         *             if list is empty or {@code index} is out of range
         */
        default T pop(int index) {
            T t = getItem(index);
            delItem(index);
            return t;
        }

        /**
         * Removes the first occurrence of {@code value}.
         * 
         * @param value
         *            - The value to remove
         * @throws ValueError
         *             if {@code value} is not present
         */
        default void remove(T value) {
            delItem(index(value));
        }

        @SuppressWarnings("unchecked")
        @Override
        default Mutable<T> iAdd(Object other) {
            this.extend((Iterable<T>) other);
            return this;
        }

    }

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
        AtomicInteger index = new AtomicInteger();
        return new PythonIterator<T>() {

            @Override
            public boolean hasNext() {
                return index.get() < length();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return getItem(index.getAndIncrement());
            }

        };
    }

    @Override
    default boolean contains(T item) {
        Predicate<T> checker = other -> item.equals(other);
        if (item == null) {
            checker = Objects::isNull;
        }
        for (T element : this) {
            if (checker.test(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    default PythonIterator<T> reversedIterator() {
        AtomicInteger index = new AtomicInteger(length() - 1);
        return new PythonIterator<T>() {

            @Override
            public boolean hasNext() {
                return index.get() >= 0;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return getItem(index.getAndDecrement());
            }

        };
    }

    /**
     * Returns the first index of value. Raises ValueError if the value is not
     * present.
     * 
     * @param value
     *            - The value to find
     * @return The first index of value
     * @throws ValueError
     *             if the value is not present
     */
    default int index(T value) {
        for (int i = 0; i < length(); i++) {
            if (Objects.equals(value, getItem(i))) {
                return i;
            }
        }
        throw new ValueError();
    }

    /**
     * Returns the number of instances of value.
     * 
     * @param value
     *            - The value to count
     * @return The number of value instances
     */
    default int count(T value) {
        return StreamSupport.stream(spliterator(), false)
                .filter(x -> Objects.equals(x, value)).mapToInt(e -> 1).sum();
    }

    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(iterator(), length(), 0);
    }

}
