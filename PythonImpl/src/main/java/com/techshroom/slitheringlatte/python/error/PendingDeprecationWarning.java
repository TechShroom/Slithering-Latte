package com.techshroom.slitheringlatte.python.error;

/**
 * Base class for warnings about features which will be deprecated in the
 * future.
 */
public class PendingDeprecationWarning extends Warning {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new PendingDeprecationWarning with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public PendingDeprecationWarning(Object... args) {
        super(args);
    }

    /**
     * Creates a new PendingDeprecationWarning with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public PendingDeprecationWarning(String message) {
        super(message);
    }

    @Override
    public PendingDeprecationWarning with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
