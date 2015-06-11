package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.error.NotImplementedError;
import com.techshroom.slitheringlatte.python.interfaces.DunderInterface;

public interface ConvertableToInt extends DunderInterface {
    @MethodType(MethodType.Value.METHOD)
    @PythonName("__int__")
    default int int$() {
        throw new NotImplementedError();
    }
}