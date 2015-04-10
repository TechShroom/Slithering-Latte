package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import java.util.Map;

@PythonName("__annotations__")
public interface ParameterAnnotationsAttribute extends Writable {
    void annotations(Map<String, Object> annotations);

    Map<String, Object> annotations();
}
