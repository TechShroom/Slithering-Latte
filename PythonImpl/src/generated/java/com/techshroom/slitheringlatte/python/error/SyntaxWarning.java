package com.techshroom.slitheringlatte.python.error;

/**
 * Base class for warnings about dubious syntax
 */
public class SyntaxWarning extends Warning {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new SyntaxWarning with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public SyntaxWarning(Object... args) {
        super(args);
    }

    /**
     * Creates a new SyntaxWarning with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public SyntaxWarning(String message) {
        super(message);
    }

    @Override
    public SyntaxWarning with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
