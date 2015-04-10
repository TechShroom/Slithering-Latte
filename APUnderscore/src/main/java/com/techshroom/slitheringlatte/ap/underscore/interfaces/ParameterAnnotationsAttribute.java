package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import java.util.Map;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__annotations__")
public interface ParameterAnnotationsAttribute extends Writable {
    void annotations(Map<String, Object> annotations);

    Map<String, Object> annotations();
}
