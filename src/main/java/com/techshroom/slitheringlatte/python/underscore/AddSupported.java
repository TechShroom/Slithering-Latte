package com.techshroom.slitheringlatte.python.underscore;

/**
 * Interface marker for __add__'s Java equivalent, {@link #add()}.
 * 
 * @author Kenzie Togami
 */
public interface AddSupported {
    static double add(Object o) {
        if (o instanceof AddSupported) {
            return ((AddSupported) o).add();
        } else {
            throw new UnsupportedOperationException(o + " does not support add");
        }
    }

    double add();
}
