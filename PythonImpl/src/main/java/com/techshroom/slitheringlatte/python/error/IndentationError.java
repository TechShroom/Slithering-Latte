package com.techshroom.slitheringlatte.python.error;

/**
 * Base class for syntax errors related to incorrect indentation. This is a
 * subclass of {@link SyntaxError}.
 */
public class IndentationError extends SyntaxError {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new IndentationError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public IndentationError(Object... args) {
        super(args);
    }

    /**
     * Creates a new IndentationError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public IndentationError(String message) {
        super(message);
    }

    @Override
    public IndentationError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
