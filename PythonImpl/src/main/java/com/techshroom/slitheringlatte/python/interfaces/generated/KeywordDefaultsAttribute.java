package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.Writable;

import java.util.Map;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__kwdefaults__")
public interface KeywordDefaultsAttribute extends Writable {
    void kwdefaults(Map<String, Object> kwdefaults);

    Map<String, Object> kwdefaults();
}
