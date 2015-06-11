package com.techshroom.slitheringlatte.python.error;

/**
 * This exception is derived from {@link RuntimeError}. In user defined base
 * classes, abstract methods should raise this exception when they require
 * derived classes to override the method.
 */
public class NotImplementedError extends RuntimeError {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new NotImplementedError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public NotImplementedError(Object... args) {
        super(args);
    }

    /**
     * Creates a new NotImplementedError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public NotImplementedError(String message) {
        super(message);
    }

    @Override
    public NotImplementedError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
