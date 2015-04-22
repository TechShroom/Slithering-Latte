package com.techshroom.slitheringlatte.compiler;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.techshroom.slitheringlatte.array.EmptyArray;
import com.techshroom.slitheringlatte.codeobjects.JavaCodeContainer;
import com.techshroom.slitheringlatte.codeobjects.Language;
import com.techshroom.slitheringlatte.codeobjects.PythonCodeContainer;
import com.techshroom.slitheringlatte.codeobjects.generators.CodeFactory;
import com.techshroom.slitheringlatte.codeobjects.generators.PythonCodeFactory;

/**
 * Code compiler implementation.
 * 
 * @author Kenzie Togami
 */
public class CompilerImpl implements Compiler {
    private static final Optional<JavaCodeContainer[]> JCC_EMPTY_ARRAY =
            EmptyArray.of(JavaCodeContainer.class).getRegular();
    private final String inSrc, outTarget;
    private final PythonCodeContainer[] pythonContainers;
    private final transient List<PythonCodeContainer> contList;
    private final CodeFactory factory;

    /**
     * Create a new compiler implementation.
     * 
     * @param in
     *            - input source
     * @param out
     *            - output target
     * @param factory
     *            - factory to generate python code
     * @param genericFactory
     *            - factory to wrap strings
     */
    @Inject
    public CompilerImpl(@Named("inputSource") String in,
            @Named("outputTarget") String out, PythonCodeFactory factory,
            CodeFactory genericFactory) {
        this.inSrc = in;
        this.outTarget = out;
        this.pythonContainers = factory.fromStringDescriptor(in);
        this.contList = ImmutableList.copyOf(this.pythonContainers);
        this.factory = genericFactory;
    }

    @Override
    public JavaCodeContainer[] generateJavaCode() {
        for (PythonCodeContainer python : this.pythonContainers) {
            checkNotNull(python);
            if (python.isLoadable()) {
                python.asLoadable().get().load();
            }
        }
        // for now, just read the containers and try to process them
        List<JavaCodeContainer> javaCode =
                new ArrayList<>(this.pythonContainers.length);
        for (PythonCodeContainer pythonCodeContainer : this.pythonContainers) {
            javaCode.add(generateJavaCode(pythonCodeContainer));
        }
        return javaCode.toArray(JCC_EMPTY_ARRAY.get());
    }

    private JavaCodeContainer generateJavaCode(PythonCodeContainer python) {
        String java = "";
        return this.factory.wrap(java, Language.JAVA);
    }
}
