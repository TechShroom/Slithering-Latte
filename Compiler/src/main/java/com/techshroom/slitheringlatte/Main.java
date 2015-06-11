package com.techshroom.slitheringlatte;

import static com.google.common.base.Preconditions.checkNotNull;
import joptsimple.OptionSet;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.techshroom.slitheringlatte.codeobjects.JavaCodeContainer;
import com.techshroom.slitheringlatte.codeobjects.generators.CodeFactory;
import com.techshroom.slitheringlatte.codeobjects.generators.CodeFactoryImpl;
import com.techshroom.slitheringlatte.codeobjects.generators.PythonCodeFactory;
import com.techshroom.slitheringlatte.codeobjects.generators.PythonCodeFactoryImpl;
import com.techshroom.slitheringlatte.compiler.Compiler;
import com.techshroom.slitheringlatte.compiler.CompilerImpl;

/**
 * Main class for Slithering Latte.
 * 
 * @author Kenzie Togami
 */
public class Main {

    /**
     * Module for binding.
     * 
     * @author Kenzie Togami
     */
    public static final class Module extends AbstractModule {

        private String input = "", output = "";

        @Override
        protected void configure() {
            bind(PythonCodeFactory.class).to(PythonCodeFactoryImpl.class);
            bind(CodeFactory.class).to(CodeFactoryImpl.class);
            bind(Compiler.class).to(CompilerImpl.class);
        }

        /**
         * Set the input source.
         * 
         * @param input
         *            - input source
         */
        public void setInput(String input) {
            this.input = checkNotNull(input);
        }

        /**
         * Get/provide the input source value.
         * 
         * @return the input source
         */
        @Provides
        @Named("inputSource")
        public String getInput() {
            return this.input;
        }

        /**
         * Set the output target.
         * 
         * @param output
         *            - output target
         */
        public void setOutput(String output) {
            this.output = checkNotNull(output);
        }

        /**
         * Get/provide the output target value.
         * 
         * @return the output target
         */
        @Provides
        @Named("outputTarget")
        public String getOutput() {
            return this.output;
        }
    }

    /**
     * Reference to the module used in the injector, for setting injectable
     * values.
     */
    public static final Module moduleRef = new Module();
    /**
     * Main injector. Please, please, don't use this unless you need to.
     */
    public static final Injector INJECTOR = Guice.createInjector(moduleRef);

    /**
     * Start a new compiler.
     * 
     * @param args
     *            - arguments.
     */
    public static void main(String[] args) {
        Options opt = Options.INSTANCE;
        OptionSet options = opt.parse(args);
        main(opt.inputOpt().value(options), opt.outputOpt().value(options));
    }

    /**
     * Start a new compiler.
     * 
     * @param input
     *            - input is either {@link Options#STREAM} or a file
     * @param output
     *            - output is either {@link Options#STREAM} or a file
     */
    public static void main(String input, String output) {
        moduleRef.setInput(input);
        moduleRef.setOutput(output);
        Compiler compiler = INJECTOR.getInstance(Compiler.class);
        JavaCodeContainer[] javaCode = compiler.generateJavaCode();
    }
}
