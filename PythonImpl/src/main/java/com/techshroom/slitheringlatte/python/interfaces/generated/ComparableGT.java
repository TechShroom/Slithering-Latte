package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.DunderAttribute;

@InterfaceType(InterfaceType.Value.METHOD)
@PythonName("__gt__")
public interface ComparableGT<T> extends DunderAttribute {
    boolean greaterThan(T other);
}
