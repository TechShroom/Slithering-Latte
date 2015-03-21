package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import java.util.Map;

@PythonName("__kwdefaults__")
public interface KeywordDefaultsAttribute extends Writable {
    default void __kwdefaults__(Map __kwdefaults__) {
        throw new UnsupportedOperationException("__kwdefaults__ not implemented");
    }

    default Map __kwdefaults__() {
        return null;
    }
}
