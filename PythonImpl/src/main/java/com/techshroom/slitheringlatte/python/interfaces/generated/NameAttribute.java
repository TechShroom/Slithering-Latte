package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.Writable;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__name__")
public interface NameAttribute extends Writable {
    void name(String name);

    String name();
}
