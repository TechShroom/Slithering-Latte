package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when indentation contains an inconsistent use of tabs and spaces. This
 * is a subclass of {@link IndentationError}.
 */
public class TabError extends IndentationError {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new TabError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public TabError(Object... args) {
        super(args);
    }

    /**
     * Creates a new TabError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public TabError(String message) {
        super(message);
    }

    @Override
    public TabError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
