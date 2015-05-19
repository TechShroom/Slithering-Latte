package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a <a class="reference internal"
 * href="https://docs.python.org/3/glossary.html#term-generator">
 * <em class="xref std std-term">generator</em></a>‘s
 * <tt class="xref py py-meth docutils literal"><span class="pre">close()</span></tt>
 * method is called. It directly inherits from {@link BaseException} instead of
 * {@link Exception} since it is technically not an error.
 */
public class GeneratorExit extends BaseException {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new GeneratorExit with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public GeneratorExit(Object... args) {
        super(args);
    }

    /**
     * Creates a new GeneratorExit with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public GeneratorExit(String message) {
        super(message);
    }

    @Override
    public GeneratorExit with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
