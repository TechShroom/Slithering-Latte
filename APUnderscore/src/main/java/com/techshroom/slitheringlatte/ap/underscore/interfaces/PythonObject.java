package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@InterfaceType(InterfaceType.Value.MIX)
@PythonName("object")
public interface PythonObject<T> extends Deletable, Representable, ConvertableToBytes, ComparableMixin<T>, ConvertableToBool {
}
