package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import java.util.Map;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__kwdefaults__")
public interface KeywordDefaultsAttribute extends Writable {
    void kwdefaults(Map<String, Object> kwdefaults);

    Map<String, Object> kwdefaults();
}
