package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__lt__")
public interface ComparableLT<T> extends DunderAttribute {
    boolean lessThan(T other);
}
