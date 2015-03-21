package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import java.util.Map;

@PythonName("__dict__")
public interface AttributeDictionaryAttribute extends Writable {
    default void __dict__(Map __dict__) {
        throw new UnsupportedOperationException("__dict__ not implemented");
    }

    default Map __dict__() {
        return null;
    }
}
