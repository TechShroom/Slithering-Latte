package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.DunderAttribute;

@InterfaceType(InterfaceType.Value.METHOD)
@PythonName("__ge__")
public interface ComparableGE<T> extends DunderAttribute {
    boolean greaterThanOrEqualTo(T other);
}
