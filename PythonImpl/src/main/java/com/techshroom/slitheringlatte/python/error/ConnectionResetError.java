package com.techshroom.slitheringlatte.python.error;

/**
 * A subclass of {@link ConnectionError}, raised when a connection is reset by
 * the peer. Corresponds to
 * <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt>
 * <tt class="docutils literal"><span class="pre">ECONNRESET</span></tt>.
 */
public class ConnectionResetError extends ConnectionError {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ConnectionResetError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public ConnectionResetError(Object... args) {
        super(args);
    }

    /**
     * Creates a new ConnectionResetError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public ConnectionResetError(String message) {
        super(message);
    }

    @Override
    public ConnectionResetError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
