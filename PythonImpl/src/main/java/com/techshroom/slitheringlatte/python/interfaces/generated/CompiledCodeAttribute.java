package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.Writable;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__code__")
public interface CompiledCodeAttribute extends Writable {
    void code(String code);

    String code();
}
