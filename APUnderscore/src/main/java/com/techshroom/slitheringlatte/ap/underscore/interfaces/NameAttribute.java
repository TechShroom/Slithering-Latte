package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__name__")
public interface NameAttribute extends Writable {
    void name(String name);

    String name();
}
