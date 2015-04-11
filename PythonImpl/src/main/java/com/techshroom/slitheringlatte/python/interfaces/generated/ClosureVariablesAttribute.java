package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.Tuple;
import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.DunderAttribute;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__closure__")
public interface ClosureVariablesAttribute extends DunderAttribute {
    Tuple closure();
}
