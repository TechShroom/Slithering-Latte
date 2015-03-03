package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a Unicode-related error occurs during encoding. It is a subclass of {@link UnicodeError}.
 */
public class UnicodeEncodeError extends UnicodeError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new UnicodeEncodeError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public UnicodeEncodeError(Object... args) {
        super(args);
    }

    /**
     * Creates a new UnicodeEncodeError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public UnicodeEncodeError(String message) {
        super(message);
    }

    @Override
    public UnicodeEncodeError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
