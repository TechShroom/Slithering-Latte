package com.techshroom.slitheringlatte.ap.underscore.interfaces.generated;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.ap.underscore.interfaces.DunderAttribute;
import java.util.Map;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__global__")
public interface GlobalsAttribute extends DunderAttribute {
    Map<String, Object> global();
}
