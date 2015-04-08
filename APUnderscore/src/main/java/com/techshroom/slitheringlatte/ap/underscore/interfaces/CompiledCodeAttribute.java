package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__code__")
public interface CompiledCodeAttribute extends Writable {
    void code(String code);

    String code();
}
