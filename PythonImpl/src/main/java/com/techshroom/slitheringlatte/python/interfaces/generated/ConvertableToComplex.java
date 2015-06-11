package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.abdulfatir.jcomplexnumber.ComplexNumber;
import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.error.NotImplementedError;
import com.techshroom.slitheringlatte.python.interfaces.DunderInterface;

public interface ConvertableToComplex extends DunderInterface {

    @MethodType(MethodType.Value.METHOD)
    @PythonName("__complex__")
    default ComplexNumber complex() {
        throw new NotImplementedError();
    }
}
