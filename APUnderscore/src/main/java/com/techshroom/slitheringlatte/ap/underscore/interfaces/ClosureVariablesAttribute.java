package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.python.Tuple;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__closure__")
public interface ClosureVariablesAttribute extends DunderAttribute {
    Tuple closure();
}
