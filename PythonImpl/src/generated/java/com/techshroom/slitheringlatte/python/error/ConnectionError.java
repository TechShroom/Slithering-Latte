package com.techshroom.slitheringlatte.python.error;

/**
 * A base class for connection-related issues.<p>Subclasses are {@link BrokenPipeError}, {@link ConnectionAbortedError}, {@link ConnectionRefusedError} and {@link ConnectionResetError}.</p>
 */
public class ConnectionError extends OSError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ConnectionError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public ConnectionError(Object... args) {
        super(args);
    }

    /**
     * Creates a new ConnectionError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public ConnectionError(String message) {
        super(message);
    }

    @Override
    public ConnectionError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
