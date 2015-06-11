package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.interfaces.ComparableMixin;

public interface PythonObject<T> extends Deletable, Representable,
        ConvertableToBytes, ComparableMixin<T>, ConvertableToBool,
        AttributeHolder {
}
