package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.Tuple;
import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.WritableAttribute;
import java.lang.String;
import java.util.Map;

public interface CallableType extends WritableAttribute, AttributeHolder {
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
    @PythonName("__qualname__")
    String qualname();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__qualname__")
    void qualname(String qualname);

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__module__")
    String module();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__module__")
    void module(String module);

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__defaults__")
    Tuple defaults();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__defaults__")
    void defaults(Tuple defaults);

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__code__")
    String code();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__code__")
    void code(String code);

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__global__")
    Map<String, Object> global();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__global__")
    void global(Map<String, Object> global);

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__closure__")
    Tuple closure();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__closure__")
    void closure(Tuple closure);

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__annotations__")
    Map<String, Object> annotations();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__annotations__")
    void annotations(Map<String, Object> annotations);

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__kwdefaults__")
    Map<String, Object> kwdefaults();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__kwdefaults__")
    void kwdefaults(Map<String, Object> kwdefaults);
}
