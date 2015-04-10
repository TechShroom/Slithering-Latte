package com.techshroom.slitheringlatte.ap.underscore.interfaces.generated;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.ap.underscore.interfaces.Writable;
import java.util.Map;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__dict__")
public interface AttributeDictionaryAttribute extends Writable {
    void dict(Map<String, Object> dict);

    Map<String, Object> dict();
}
