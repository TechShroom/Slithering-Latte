package com.techshroom.slitheringlatte.python.error;

/**
 * A subclass of {@link ConnectionError}, raised when trying to write on a pipe
 * while the other end has been closed, or trying to write on a socket which has
 * been shutdown for writing. Corresponds to
 * <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt>
 * <tt class="docutils literal"><span class="pre">EPIPE</span></tt> and
 * <tt class="docutils literal"><span class="pre">ESHUTDOWN</span></tt>.
 */
public class BrokenPipeError extends ConnectionError {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new BrokenPipeError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public BrokenPipeError(Object... args) {
        super(args);
    }

    /**
     * Creates a new BrokenPipeError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public BrokenPipeError(String message) {
        super(message);
    }

    @Override
    public BrokenPipeError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
