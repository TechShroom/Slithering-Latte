package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a Unicode-related error occurs during translating. It is a subclass of {@link UnicodeError}.
 */
public class UnicodeTranslateError extends UnicodeError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new UnicodeTranslateError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public UnicodeTranslateError(Object... args) {
        super(args);
    }

    /**
     * Creates a new UnicodeTranslateError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public UnicodeTranslateError(String message) {
        super(message);
    }

    @Override
    public UnicodeTranslateError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
