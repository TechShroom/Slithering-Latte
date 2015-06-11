package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a local or global name is not found. This applies only to unqualified names. The associated value is an error message that includes the name that could not be found.
 */
public class NameError extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new NameError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public NameError(Object... args) {
        super(args);
    }

    /**
     * Creates a new NameError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public NameError(String message) {
        super(message);
    }

    @Override
    public NameError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
