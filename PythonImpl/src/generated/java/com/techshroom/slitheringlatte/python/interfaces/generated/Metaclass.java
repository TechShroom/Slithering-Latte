package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.KeywordArgs;
import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.DunderInterface;
import java.lang.String;
import java.util.List;
import java.util.Map;

public interface Metaclass extends DunderInterface, CallableType {
    @MethodType(MethodType.Value.METHOD)
    @PythonName("__prepare__")
    default Map<String, Object> prepare(String name, List<String> bases, @KeywordArgs Map<String, Object> kwargs) {
        return new java.util.HashMap<>();
    }

    @MethodType(MethodType.Value.METHOD)
    @PythonName("__instancecheck__")
    boolean instancecheck(String instance);

    @MethodType(MethodType.Value.METHOD)
    @PythonName("__subclasscheck__")
    boolean subclasscheck(String subclass);
}
