package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("object")
public interface PythonObject extends Deletable, Representable, ConvertableToBytes, ComparableMixin, ConvertableToBool {
}
