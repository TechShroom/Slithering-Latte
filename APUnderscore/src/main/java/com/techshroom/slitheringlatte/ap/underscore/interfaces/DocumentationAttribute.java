package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__doc__")
public interface DocumentationAttribute extends Writable {
    default void __doc__(String __doc__) {
        throw new UnsupportedOperationException("__doc__ not implemented");
    }

    default String __doc__() {
        return null;
    }
}
