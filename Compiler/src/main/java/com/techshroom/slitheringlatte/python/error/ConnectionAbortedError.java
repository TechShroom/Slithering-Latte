package com.techshroom.slitheringlatte.python.error;

/**
 * A subclass of {@link ConnectionError}, raised when a connection attempt is aborted by the peer. Corresponds to <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt> <tt class="docutils literal"><span class="pre">ECONNABORTED</span></tt>.
 */
public class ConnectionAbortedError extends ConnectionError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ConnectionAbortedError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public ConnectionAbortedError(Object... args) {
        super(args);
    }

    /**
     * Creates a new ConnectionAbortedError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public ConnectionAbortedError(String message) {
        super(message);
    }

    @Override
    public ConnectionAbortedError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
