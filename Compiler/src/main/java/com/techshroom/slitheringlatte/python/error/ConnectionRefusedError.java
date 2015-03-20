package com.techshroom.slitheringlatte.python.error;

/**
 * A subclass of {@link ConnectionError}, raised when a connection attempt is refused by the peer. Corresponds to <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt> <tt class="docutils literal"><span class="pre">ECONNREFUSED</span></tt>.
 */
public class ConnectionRefusedError extends ConnectionError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ConnectionRefusedError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public ConnectionRefusedError(Object... args) {
        super(args);
    }

    /**
     * Creates a new ConnectionRefusedError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public ConnectionRefusedError(String message) {
        super(message);
    }

    @Override
    public ConnectionRefusedError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
