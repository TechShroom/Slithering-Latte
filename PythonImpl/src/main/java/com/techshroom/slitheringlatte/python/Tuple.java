package com.techshroom.slitheringlatte.python;

import java.util.AbstractList;
import java.util.stream.Collectors;

/**
 * Tuple implementation in Java.
 * 
 * @author Kenzie Togami
 */
public final class Tuple
        extends AbstractList<Object> {
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
        this.contents = from;
    }

    @SuppressWarnings("javadoc")
    public int len() {
        return size();
    }

    @Override
    public Object get(int index) {
        return this.contents[index];
    }

    @Override
    public int size() {
        return this.contents.length;
    }

    @Override
    public String toString() {
        switch (len()) {
            case 0:
                return "()";
            case 1:
                return "(" + get(0) + ",)";
            default:
                return stream().map(String::valueOf)
                        .collect(Collectors.joining(", ", "(", ")"));
        }
    }
}
