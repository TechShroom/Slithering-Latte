package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__module__")
public interface ModuleNameAttribute extends Writable {
    default void __module__(String __module__) {
        throw new UnsupportedOperationException("__module__ not implemented");
    }

    default String __module__() {
        return null;
    }
}
