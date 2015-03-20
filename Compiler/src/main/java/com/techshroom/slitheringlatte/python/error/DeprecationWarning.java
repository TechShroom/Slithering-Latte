package com.techshroom.slitheringlatte.python.error;

/**
 * Base class for warnings about deprecated features.
 */
public class DeprecationWarning extends Warning {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new DeprecationWarning with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public DeprecationWarning(Object... args) {
        super(args);
    }

    /**
     * Creates a new DeprecationWarning with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public DeprecationWarning(String message) {
        super(message);
    }

    @Override
    public DeprecationWarning with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
