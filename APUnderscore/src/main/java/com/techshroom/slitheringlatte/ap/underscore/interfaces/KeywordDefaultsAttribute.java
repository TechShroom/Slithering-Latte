package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import java.util.Map;

@PythonName("__kwdefaults__")
public interface KeywordDefaultsAttribute<K, V> extends Writable {
    void kwdefaults(Map<K, V> kwdefaults);

    Map<K, V> kwdefaults();
}
