package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.Tuple;
import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.Writable;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__defaults__")
public interface DefaultArgsAttribute extends Writable {
    void defaults(Tuple defaults);

    Tuple defaults();
}
