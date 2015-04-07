package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__func__")
public interface BoundFunctionAttribute extends DunderAttribute {
    default Object func() {
        return null;
    }
}
