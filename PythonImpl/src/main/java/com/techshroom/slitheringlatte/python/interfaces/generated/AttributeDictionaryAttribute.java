package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.Writable;

import java.util.Map;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__dict__")
public interface AttributeDictionaryAttribute extends Writable {
    void dict(Map<String, Object> dict);

    Map<String, Object> dict();
}
