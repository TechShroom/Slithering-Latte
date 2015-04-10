package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__eq__")
public interface ComparableEQ<T> extends DunderAttribute {
    boolean equalTo(T other);
}
