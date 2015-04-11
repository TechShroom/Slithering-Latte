package com.techshroom.slitheringlatte.ap.underscore.interfaces.generated;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.ap.underscore.interfaces.DunderAttribute;
import java.util.List;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__slots__")
public interface AttributeSlots extends DunderAttribute {
    List<String> slots();
}
