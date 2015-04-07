package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__module__")
public interface ModuleNameAttribute extends Writable {
    default void module(String module) {
        throw new UnsupportedOperationException("module not implemented");
    }

    default String module() {
        return null;
    }
}
