package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a reference is made to a local variable in a function or method, but no value has been bound to that variable. This is a subclass of {@link NameError}.
 */
public class UnboundLocalError extends NameError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new UnboundLocalError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public UnboundLocalError(Object... args) {
        super(args);
    }

    /**
     * Creates a new UnboundLocalError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public UnboundLocalError(String message) {
        super(message);
    }

    @Override
    public UnboundLocalError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
