package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__bool__")
public interface ConvertableToBool extends DunderAttribute {
    boolean bool();
}
