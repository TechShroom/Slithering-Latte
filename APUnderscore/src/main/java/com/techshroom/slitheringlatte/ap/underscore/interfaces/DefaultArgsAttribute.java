package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.python.Tuple;

@PythonName("__defualts__")
public interface DefaultArgsAttribute extends Writable {
    void defualts(Tuple defualts);

    Tuple defualts();
}
