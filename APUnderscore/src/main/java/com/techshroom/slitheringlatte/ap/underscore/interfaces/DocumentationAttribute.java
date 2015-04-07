package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__doc__")
public interface DocumentationAttribute extends Writable {
    default void doc(String doc) {
        throw new UnsupportedOperationException("doc not implemented");
    }

    default String doc() {
        return null;
    }
}
