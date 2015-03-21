package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__code__")
public interface CompiledCodeAttribute extends Writable {
    default void __code__(String __code__) {
        throw new UnsupportedOperationException("__code__ not implemented");
    }

    default String __code__() {
        return null;
    }
}
