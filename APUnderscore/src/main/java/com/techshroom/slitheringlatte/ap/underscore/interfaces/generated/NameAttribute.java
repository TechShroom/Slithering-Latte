package com.techshroom.slitheringlatte.ap.underscore.interfaces.generated;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.ap.underscore.interfaces.Writable;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__name__")
public interface NameAttribute extends Writable {
    void name(String name);

    String name();
}
