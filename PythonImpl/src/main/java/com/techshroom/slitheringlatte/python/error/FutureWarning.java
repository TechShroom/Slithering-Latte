package com.techshroom.slitheringlatte.python.error;

/**
 * Base class for warnings about constructs that will change semantically in the
 * future.
 */
public class FutureWarning extends Warning {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new FutureWarning with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public FutureWarning(Object... args) {
        super(args);
    }

    /**
     * Creates a new FutureWarning with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public FutureWarning(String message) {
        super(message);
    }

    @Override
    public FutureWarning with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
