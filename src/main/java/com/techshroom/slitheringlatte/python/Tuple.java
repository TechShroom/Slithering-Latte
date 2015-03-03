package com.techshroom.slitheringlatte.python;

import java.util.AbstractList;
import java.util.stream.Collectors;

import com.techshroom.slitheringlatte.python.underscore.LenSupported;
import com.techshroom.slitheringlatte.python.underscore.ReprSupported;

/**
 * Tuple implementation in Java.
 * 
 * @author Kenzie Togami
 */
public final class Tuple
        extends AbstractList<Object> implements ReprSupported.ReprStrSupported,
        LenSupported {
    final Object[] contents;

    /**
     * Create a tuple
     * 
     * @param objects
     *            - objects
     * @return a new tuple wrapping the given objects
     */
    public static final Tuple create(Object... objects) {
        return new Tuple(objects.clone());
    }

    private Tuple(Object[] from) {
        super();
        contents = from;
    }

    @Override
    public int len() {
        return size();
    }

    @Override
    public Object get(int index) {
        return contents[index];
    }

    @Override
    public int size() {
        return contents.length;
    }

    @Override
    public String toString() {
        switch (len()) {
            case 0:
                return "()";
            case 1:
                return "(" + ReprSupported.repr(get(0)) + ",)";
            default:
                return stream().map(ReprSupported::repr)
                        .collect(Collectors.joining(", ", "(", ")"));
        }
    }
}
