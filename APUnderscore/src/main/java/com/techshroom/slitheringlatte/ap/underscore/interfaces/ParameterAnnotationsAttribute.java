package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import java.util.Map;

@PythonName("__annotations__")
public interface ParameterAnnotationsAttribute<K, V> extends Writable {
    default void annotations(Map<K, V> annotations) {
        throw new UnsupportedOperationException("annotations not implemented");
    }

    default Map<K, V> annotations() {
        return null;
    }
}
