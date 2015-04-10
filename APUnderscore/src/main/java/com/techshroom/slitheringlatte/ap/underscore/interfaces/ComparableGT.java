package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__gt__")
public interface ComparableGT<T> extends DunderAttribute {
    boolean greaterThan(T other);
}
