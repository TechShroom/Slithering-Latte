package com.techshroom.slitheringlatte.python.error;

/**
 * Base class for warning categories.
 */
public class Warning extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new Warning with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public Warning(Object... args) {
        super(args);
    }

    /**
     * Creates a new Warning with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public Warning(String message) {
        super(message);
    }

    @Override
    public Warning with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
