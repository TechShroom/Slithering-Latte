package com.techshroom.slitheringlatte.python.error;

/**
 * Base class for warnings about dubious runtime behavior.
 */
public class RuntimeWarning extends Warning {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new RuntimeWarning with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public RuntimeWarning(Object... args) {
        super(args);
    }

    /**
     * Creates a new RuntimeWarning with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public RuntimeWarning(String message) {
        super(message);
    }

    @Override
    public RuntimeWarning with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
