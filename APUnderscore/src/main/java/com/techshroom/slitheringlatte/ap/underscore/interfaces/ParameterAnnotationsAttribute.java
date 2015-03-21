package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import java.util.Map;

@PythonName("__annotations__")
public interface ParameterAnnotationsAttribute extends Writable {
    default void __annotations__(Map __annotations__) {
        throw new UnsupportedOperationException("__annotations__ not implemented");
    }

    default Map __annotations__() {
        return null;
    }
}
