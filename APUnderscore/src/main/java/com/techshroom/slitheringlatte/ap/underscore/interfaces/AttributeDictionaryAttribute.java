package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import java.util.Map;

@PythonName("__dict__")
public interface AttributeDictionaryAttribute<K, V> extends Writable {
    void dict(Map<K, V> dict);

    Map<K, V> dict();
}
