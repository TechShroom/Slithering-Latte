package com.techshroom.slitheringlatte.python.interfaces.collections.abcs;

/**
 * ABC for a class that provides the method {@link #contains(Object)}.
 * 
 * @author Kenzie Togami
 * @param <I>
 *            - The type of the item
 */
public interface PythonContainer<I> {

    /**
     * Called to implement membership test operators. Should return {@code true}
     * if {@code item} is in {@code this}, {@code false} otherwise. For mapping
     * objects, this should consider the keys of the mapping rather than the
     * values or the key-item pairs.
     * 
     * For objects that don't define {@link #contains(Object)}, the membership
     * test first tries iteration via {@link Iterable#iterator()}, then the old
     * sequence iteration protocol via {@code ReadableContainer#getitem()}, see
     * this section in the language reference.
     * 
     * @param item
     *            - The item to check for
     * @return {@code true} if this container contains the item
     */
    boolean contains(I item);

}
