package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when the second argument of a division or modulo operation is zero.
 * The associated value is a string indicating the type of the operands and the
 * operation.
 */
public class ZeroDivisionError extends ArithmeticError {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ZeroDivisionError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public ZeroDivisionError(Object... args) {
        super(args);
    }

    /**
     * Creates a new ZeroDivisionError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public ZeroDivisionError(String message) {
        super(message);
    }

    @Override
    public ZeroDivisionError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
