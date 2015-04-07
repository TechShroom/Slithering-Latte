package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__name__")
public interface NameAttribute extends Writable {
    default void name(String name) {
        throw new UnsupportedOperationException("name not implemented");
    }

    default String name() {
        return null;
    }
}
