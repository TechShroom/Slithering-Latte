package com.techshroom.slitheringlatte.ap.underscore.interfaces.generated;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.ap.underscore.interfaces.ComparableMixin;

@InterfaceType(InterfaceType.Value.MIX)
@PythonName("object")
public interface PythonObject<T> extends Deletable, Representable, ConvertableToBytes, ComparableMixin<T>, ConvertableToBool, MissingAttributeProvider, RegularAttributeProvider, AttributeStorage, AttributeDestructor, AttributeSequenceProvider, AttributeSlots, AttributeDictionaryAttribute {
}
