package com.techshroom.slitheringlatte.python.interfaces.collections.abcs;

import java.util.Iterator;

import com.techshroom.slitheringlatte.python.error.StopIteration;

/**
 * ABC for classes that provides the {@link #iterator()} and {@link #next()}
 * methods. See also the definition of <a
 * href="https://docs.python.org/3/glossary.html#term-iterator">iterator</a>.
 * 
 * @author Kenzie Togami
 * @param <T>
 *            - The type of the returned objects
 */
public interface PythonIterator<T> extends Iterable<T>, Iterator<T> {

    @Override
    boolean hasNext();

    @Override
    default Iterator<T> iterator() {
        return this;
    }

    @Override
    T next();

    /**
     * Python call for {@link #next()} that follows the Python rules rather than
     * Java's.
     * 
     * @return The result of calling {@code next()} or throws a
     *         {@link StopIteration} exception if {@link #hasNext()} returns
     *         {@code false}
     */
    default T python$next() {
        if (!hasNext()) {
            throw new StopIteration();
        }
        return next();
    }

}
