package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__qualname__")
public interface QualifiedNameAttribute extends Writable {
    void qualname(String qualname);

    String qualname();
}
