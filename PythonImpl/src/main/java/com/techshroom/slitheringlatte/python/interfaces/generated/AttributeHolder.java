package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.DunderInterface;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;

public interface AttributeHolder extends DunderInterface {
    @MethodType(MethodType.Value.METHOD)
    @PythonName("__getattribute__")
    Object getattribute(String key);

    @MethodType(MethodType.Value.METHOD)
    @PythonName("__setattr__")
    Object setattr(String key, Object value);

    @MethodType(MethodType.Value.METHOD)
    @PythonName("__delattr__")
    void delattr(String key);

    @MethodType(MethodType.Value.METHOD)
    @PythonName("__dir__")
    Object dir();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__slots__")
    List<String> slots();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__dict__")
    Map<String, Object> dict();

    @MethodType(MethodType.Value.ATTRIBUTE)
    @PythonName("__dict__")
    void dict(Map<String, Object> dict);
}
