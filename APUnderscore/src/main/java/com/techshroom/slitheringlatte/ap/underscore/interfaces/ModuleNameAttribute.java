package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__module__")
public interface ModuleNameAttribute extends Writable {
    void module(String module);

    String module();
}
