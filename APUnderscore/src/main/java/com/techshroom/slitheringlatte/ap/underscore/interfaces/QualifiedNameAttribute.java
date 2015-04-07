package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__qualname__")
public interface QualifiedNameAttribute extends Writable {
    default void qualname(String qualname) {
        throw new UnsupportedOperationException("qualname not implemented");
    }

    default String qualname() {
        return null;
    }
}
