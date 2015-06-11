package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.error.NotImplementedError;
import com.techshroom.slitheringlatte.python.interfaces.DunderInterface;
import java.lang.Object;

public interface OperatorDivmod extends DunderInterface {
    @MethodType(MethodType.Value.METHOD)
    @PythonName("__divmod__")
    default Object divmod(Object other) {
        throw new NotImplementedError();
    }
}
