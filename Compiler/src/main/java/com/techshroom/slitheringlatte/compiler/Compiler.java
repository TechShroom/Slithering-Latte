package com.techshroom.slitheringlatte.compiler;

import com.techshroom.slitheringlatte.codeobjects.JavaCodeContainer;

/**
 * Python to Java compiler interface.
 * 
 * @author Kenzie Togami
 */
public interface Compiler {
    /**
     * Generate some Java from the Python given.
     * 
     * @return the produced Java code.
     */
    JavaCodeContainer[] generateJavaCode();
}
