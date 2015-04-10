package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__getattr__")
public interface MissingAttributeProvider extends DunderAttribute {
    Object getattr(String name);
}
