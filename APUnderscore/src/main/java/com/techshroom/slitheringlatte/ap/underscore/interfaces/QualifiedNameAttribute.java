package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__qualname__")
public interface QualifiedNameAttribute extends Writable {
    default void __qualname__(String __qualname__) {
        throw new UnsupportedOperationException("__qualname__ not implemented");
    }

    default String __qualname__() {
        return null;
    }
}
