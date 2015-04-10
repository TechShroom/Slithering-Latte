package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__bool__")
public interface CovertableToBool extends DunderAttribute {
    boolean bool();
}
