package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.WritableAttribute;
import java.lang.String;

public interface BuiltinMethodType extends WritableAttribute {

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__doc__")
    String doc();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__doc__")
    void doc(String doc);

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__name__")
    String name();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__name__")
    void name(String name);

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__module__")
    String module();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__module__")
    void module(String module);
}
