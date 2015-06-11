package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.DunderInterface;
import java.lang.Exception;
import java.lang.Object;

public interface ContextManager extends DunderInterface {
    @MethodType(MethodType.Value.METHOD)
    @PythonName("__enter__")
    Object enter();

    @MethodType(MethodType.Value.METHOD)
    @PythonName("__exit__")
    boolean exit(Exception raised);
}