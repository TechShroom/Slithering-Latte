package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when an operation or function is applied to an object of inappropriate
 * type. The associated value is a string giving details about the type
 * mismatch.
 */
public class TypeError extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new TypeError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public TypeError(Object... args) {
        super(args);
    }

    /**
     * Creates a new TypeError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public TypeError(String message) {
        super(message);
    }

    @Override
    public TypeError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
