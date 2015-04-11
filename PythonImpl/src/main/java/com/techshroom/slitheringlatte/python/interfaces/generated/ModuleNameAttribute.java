package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.Writable;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__module__")
public interface ModuleNameAttribute extends Writable {
    void module(String module);

    String module();
}
