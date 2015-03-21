package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__name__")
public interface NameAttribute extends Writable {
    default void __name__(String __name__) {
        throw new UnsupportedOperationException("__name__ not implemented");
    }

    default String __name__() {
        return null;
    }
}
