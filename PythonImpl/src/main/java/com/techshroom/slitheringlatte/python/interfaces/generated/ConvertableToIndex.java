package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.error.NotImplementedError;
import com.techshroom.slitheringlatte.python.interfaces.DunderInterface;

public interface ConvertableToIndex extends DunderInterface {

    @MethodType(MethodType.Value.METHOD)
    @PythonName("__index__")
    default int index() {
        throw new NotImplementedError();
    }
}
