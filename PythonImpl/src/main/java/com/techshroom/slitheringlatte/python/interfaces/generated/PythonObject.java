package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.ComparableMixin;

@InterfaceType(InterfaceType.Value.MIX)
@PythonName("object")
public interface PythonObject<T> extends Deletable, Representable,
        ConvertableToBytes, ComparableMixin<T>, ConvertableToBool,
        RegularAttributeProvider, AttributeStorage, AttributeDestructor,
        AttributeSequenceProvider, AttributeSlots, AttributeDictionaryAttribute {
}
