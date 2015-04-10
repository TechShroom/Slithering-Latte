package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__ne__")
public interface ComparableNE<T> extends DunderAttribute {
    boolean notEquals(T other);
}
