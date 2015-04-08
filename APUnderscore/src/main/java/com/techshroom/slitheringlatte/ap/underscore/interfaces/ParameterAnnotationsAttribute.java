package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import java.util.Map;

@PythonName("__annotations__")
public interface ParameterAnnotationsAttribute<K, V> extends Writable {
    void annotations(Map<K, V> annotations);

    Map<K, V> annotations();
}
