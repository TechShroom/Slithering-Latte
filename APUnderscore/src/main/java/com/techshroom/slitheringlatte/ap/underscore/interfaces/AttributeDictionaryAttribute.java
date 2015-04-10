package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import java.util.Map;

@PythonName("__dict__")
public interface AttributeDictionaryAttribute extends Writable {
    void dict(Map<String, Object> dict);

    Map<String, Object> dict();
}
