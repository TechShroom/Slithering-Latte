package com.techshroom.slitheringlatte.compiler;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.techshroom.slitheringlatte.codeobjects.JavaCodeContainer;
import com.techshroom.slitheringlatte.codeobjects.PythonCodeContainer;
import com.techshroom.slitheringlatte.codeobjects.generators.PythonCodeFactory;

/**
 * Code compiler implementation.
 * 
 * @author Kenzie Togami
 */
public class CompilerImpl implements Compiler {
    private final String inSrc, outTarget;
    private final PythonCodeContainer python;

    /**
     * Create a new compiler implementation.
     * 
     * @param in
     *            - input source
     * @param out
     *            - output target
     * @param factory
     *            - factory to generate python code
     */
    @Inject
    public CompilerImpl(@Named("inputSource") String in,
            @Named("outputTarget") String out, PythonCodeFactory factory) {
        inSrc = in;
        outTarget = out;
        python = factory.fromStringDescriptor(in);
    }

    @Override
    public JavaCodeContainer[] generateJavaCode() {
        return null;
    }
}
