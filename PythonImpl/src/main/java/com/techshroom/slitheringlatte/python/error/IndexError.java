package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a sequence subscript is out of range. (Slice indices are silently
 * truncated to fall in the allowed range; if an index is not an integer,
 * {@link TypeError} is raised.)
 */
public class IndexError extends LookupError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new IndexError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public IndexError(Object... args) {
        super(args);
    }

    /**
     * Creates a new IndexError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public IndexError(String message) {
        super(message);
    }

    @Override
    public IndexError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
