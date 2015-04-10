package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__le__")
public interface ComparableLE<T> extends DunderAttribute {
    boolean lessThanOrEqualTo(T other);
}
