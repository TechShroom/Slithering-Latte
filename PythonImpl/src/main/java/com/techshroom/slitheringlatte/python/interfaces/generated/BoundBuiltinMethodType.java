package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.WritableAttribute;
import java.lang.Object;

public interface BoundBuiltinMethodType extends WritableAttribute,
        BuiltinMethodType {

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__self__")
    Object self();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__self__")
    void self(Object self);
}
