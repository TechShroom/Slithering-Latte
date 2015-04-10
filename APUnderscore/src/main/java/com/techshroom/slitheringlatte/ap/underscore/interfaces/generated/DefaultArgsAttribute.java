package com.techshroom.slitheringlatte.ap.underscore.interfaces.generated;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.ap.underscore.interfaces.Writable;
import com.techshroom.slitheringlatte.python.Tuple;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__defualts__")
public interface DefaultArgsAttribute extends Writable {
    void defualts(Tuple defualts);

    Tuple defualts();
}
