package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when an error is detected that doesn’t fall in any of the other
 * categories. The associated value is a string indicating what precisely went
 * wrong.
 */
public class RuntimeError extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new RuntimeError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public RuntimeError(Object... args) {
        super(args);
    }

    /**
     * Creates a new RuntimeError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public RuntimeError(String message) {
        super(message);
    }

    @Override
    public RuntimeError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
