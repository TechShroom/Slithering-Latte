package com.techshroom.slitheringlatte.python.error;

/**
 * The base class for those built-in exceptions that are raised for various
 * arithmetic errors: {@link OverflowError}, {@link ZeroDivisionError},
 * {@link FloatingPointError}.
 */
public class ArithmeticError extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ArithmeticError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public ArithmeticError(Object... args) {
        super(args);
    }

    /**
     * Creates a new ArithmeticError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public ArithmeticError(String message) {
        super(message);
    }

    @Override
    public ArithmeticError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
