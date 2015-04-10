package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__bytes__")
public interface ConvertableToBytes extends DunderAttribute {
    byte[] bytes();
}
