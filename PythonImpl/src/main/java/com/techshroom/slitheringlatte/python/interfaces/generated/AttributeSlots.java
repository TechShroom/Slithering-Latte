package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.DunderAttribute;

import java.util.List;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__slots__")
public interface AttributeSlots extends DunderAttribute {
    List<String> slots();
}
