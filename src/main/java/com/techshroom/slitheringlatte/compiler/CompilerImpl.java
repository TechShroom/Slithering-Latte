package com.techshroom.slitheringlatte.compiler;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.techshroom.slitheringlatte.array.EmptyArray;
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
    private final PythonCodeContainer[] pythonContainers;

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
        pythonContainers = factory.fromStringDescriptor(in);
    }

    @Override
    public JavaCodeContainer[] generateJavaCode() {
        for (PythonCodeContainer python : pythonContainers) {
            checkNotNull(python);
            if (python.isLoadable()) {
                python.asLoadable().get().load();
            }
        }
        return EmptyArray.of(JavaCodeContainer.class).get();
    }
}
