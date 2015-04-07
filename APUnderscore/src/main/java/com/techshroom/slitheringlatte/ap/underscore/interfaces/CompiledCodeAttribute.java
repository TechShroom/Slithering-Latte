package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__code__")
public interface CompiledCodeAttribute extends Writable {
    default void code(String code) {
        throw new UnsupportedOperationException("code not implemented");
    }

    default String code() {
        return null;
    }
}
