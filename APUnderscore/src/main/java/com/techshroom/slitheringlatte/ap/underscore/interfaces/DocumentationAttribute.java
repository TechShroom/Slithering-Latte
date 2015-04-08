package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("__doc__")
public interface DocumentationAttribute extends Writable {
    void doc(String doc);

    String doc();
}
