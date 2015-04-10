package com.techshroom.slitheringlatte.ap.underscore.interfaces.generated;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.ap.underscore.interfaces.DunderAttribute;

@InterfaceType(InterfaceType.Value.METHOD)
@PythonName("__bytes__")
public interface ConvertableToBytes extends DunderAttribute {
    byte[] bytes();
}
