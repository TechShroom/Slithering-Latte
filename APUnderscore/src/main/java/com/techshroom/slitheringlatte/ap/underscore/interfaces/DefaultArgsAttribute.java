package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.python.Tuple;

@PythonName("__defualts__")
public interface DefaultArgsAttribute extends Writable {
    default void __defualts__(Tuple __defualts__) {
        throw new UnsupportedOperationException("__defualts__ not implemented");
    }

    default Tuple __defualts__() {
        return null;
    }
}
