package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.DunderInterface;
import java.lang.Object;
import java.lang.String;

public interface MissingAttributeProvider extends DunderInterface {

    @MethodType(MethodType.Value.METHOD)
    @PythonName("__getattr__")
    Object getattr(String key);
}
