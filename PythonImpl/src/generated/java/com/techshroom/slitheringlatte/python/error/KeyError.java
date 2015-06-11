package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a mapping (dictionary) key is not found in the set of existing keys.
 */
public class KeyError extends LookupError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new KeyError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public KeyError(Object... args) {
        super(args);
    }

    /**
     * Creates a new KeyError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public KeyError(String message) {
        super(message);
    }

    @Override
    public KeyError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
