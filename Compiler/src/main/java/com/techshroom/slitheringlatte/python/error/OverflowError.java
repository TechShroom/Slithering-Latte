package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when the result of an arithmetic operation is too large to be represented. This cannot occur for integers (which would rather raise {@link MemoryError} than give up). However, for historical reasons, OverflowError is sometimes raised for integers that are outside a required range. Because of the lack of standardization of floating point exception handling in C, most floating point operations are not checked.
 */
public class OverflowError extends ArithmeticError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new OverflowError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public OverflowError(Object... args) {
        super(args);
    }

    /**
     * Creates a new OverflowError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public OverflowError(String message) {
        super(message);
    }

    @Override
    public OverflowError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
