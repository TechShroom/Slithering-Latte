package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__self__")
public interface BoundSelfParameterAttribute extends DunderAttribute {
    default Object self() {
        return null;
    }
}
