package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__code__")
public interface CompiledCodeAttribute extends Writable {
    void code(String code);

    String code();
}
