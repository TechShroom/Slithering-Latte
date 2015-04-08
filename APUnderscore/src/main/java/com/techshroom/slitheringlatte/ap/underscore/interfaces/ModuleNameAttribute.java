package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__module__")
public interface ModuleNameAttribute extends Writable {
    void module(String module);

    String module();
}
