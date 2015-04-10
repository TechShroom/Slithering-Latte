package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@InterfaceType(InterfaceType.Value.METHOD)
@PythonName("__bool__")
public interface ConvertableToBool extends DunderAttribute {
    boolean bool();
}
