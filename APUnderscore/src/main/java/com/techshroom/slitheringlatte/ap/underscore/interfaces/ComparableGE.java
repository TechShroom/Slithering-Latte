package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@InterfaceType(InterfaceType.Value.METHOD)
@PythonName("__ge__")
public interface ComparableGE<T> extends DunderAttribute {
    boolean greaterThanOrEqualTo(T other);
}
