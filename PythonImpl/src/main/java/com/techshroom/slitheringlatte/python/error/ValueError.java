package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a built-in operation or function receives an argument that has
 * the right type but an inappropriate value, and the situation is not described
 * by a more precise exception such as {@link IndexError}.
 */
public class ValueError extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ValueError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public ValueError(Object... args) {
        super(args);
    }

    /**
     * Creates a new ValueError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public ValueError(String message) {
        super(message);
    }

    @Override
    public ValueError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
