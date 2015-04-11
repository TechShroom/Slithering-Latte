package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.Writable;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__doc__")
public interface DocumentationAttribute extends Writable {
    void doc(String doc);

    String doc();
}
