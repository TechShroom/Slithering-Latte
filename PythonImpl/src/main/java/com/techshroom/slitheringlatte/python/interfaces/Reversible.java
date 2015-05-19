package com.techshroom.slitheringlatte.python.interfaces;

import com.techshroom.slitheringlatte.python.interfaces.collections.abcs.PythonIterator;

/**
 * ABC for a class that provides the method {@link #reversedIterator()}.
 * 
 * @author Kenzie Togami
 * @param <T>
 *            - The type of the reversed iterator's contents, probably the same
 *            as this type
 */
public interface Reversible<T> {

    /**
     * Called to implement behavior for the reversed() built in function. Should
     * return a reversed version of the sequence. Implement this only if the
     * sequence class is ordered, like list or tuple.
     * 
     * @return A reversed version of the sequence
     */
    PythonIterator<T> reversedIterator();

}
