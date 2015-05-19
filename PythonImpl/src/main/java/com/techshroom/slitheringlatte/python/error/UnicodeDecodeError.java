package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a Unicode-related error occurs during decoding. It is a subclass
 * of {@link UnicodeError}.
 */
public class UnicodeDecodeError extends UnicodeError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new UnicodeDecodeError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public UnicodeDecodeError(Object... args) {
        super(args);
    }

    /**
     * Creates a new UnicodeDecodeError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public UnicodeDecodeError(String message) {
        super(message);
    }

    @Override
    public UnicodeDecodeError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
