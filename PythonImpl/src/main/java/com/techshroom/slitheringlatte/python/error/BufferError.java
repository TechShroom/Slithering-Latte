package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a <a class="reference internal"
 * href="https://docs.python.org/3/c-api/buffer.html#bufferobjects">
 * <em>buffer</em></a> related operation cannot be performed.
 */
public class BufferError extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new BufferError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public BufferError(Object... args) {
        super(args);
    }

    /**
     * Creates a new BufferError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public BufferError(String message) {
        super(message);
    }

    @Override
    public BufferError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
