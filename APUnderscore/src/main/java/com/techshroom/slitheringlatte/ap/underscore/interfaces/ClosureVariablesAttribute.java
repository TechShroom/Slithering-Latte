package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.python.Tuple;

@PythonName("__closure__")
public interface ClosureVariablesAttribute extends DunderAttribute {
    default Tuple __closure__() {
        return null;
    }
}
