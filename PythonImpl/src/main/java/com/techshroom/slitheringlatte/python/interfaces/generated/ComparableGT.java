package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.DunderInterface;

public interface ComparableGT<T> extends DunderInterface {

    @MethodType(MethodType.Value.METHOD)
    @PythonName("__gt__")
    boolean greaterThan(T other);
}
