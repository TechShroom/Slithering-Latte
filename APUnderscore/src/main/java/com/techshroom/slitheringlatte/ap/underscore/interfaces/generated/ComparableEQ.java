package com.techshroom.slitheringlatte.ap.underscore.interfaces.generated;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.ap.underscore.interfaces.DunderAttribute;

@InterfaceType(InterfaceType.Value.METHOD)
@PythonName("__eq__")
public interface ComparableEQ<T> extends DunderAttribute {
    boolean equalTo(T other);
}
