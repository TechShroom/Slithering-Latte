package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import java.util.Map;

@PythonName("__kwdefaults__")
public interface KeywordDefaultsAttribute<K, V> extends Writable {
    default void kwdefaults(Map<K, V> kwdefaults) {
        throw new UnsupportedOperationException("kwdefaults not implemented");
    }

    default Map<K, V> kwdefaults() {
        return null;
    }
}
