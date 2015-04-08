package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__name__")
public interface NameAttribute extends Writable {
    void name(String name);

    String name();
}
