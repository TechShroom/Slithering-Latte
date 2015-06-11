package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.DunderInterface;
import java.lang.Object;

public interface OperatorPow extends DunderInterface {

    @MethodType(MethodType.Value.METHOD)
    @PythonName("__pow__")
    Object pow(Object other);

    @MethodType(MethodType.Value.METHOD)
    @PythonName("__pow__")
    Object pow(Object other, Object mod);
}
