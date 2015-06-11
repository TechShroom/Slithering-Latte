package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a system function timed out at the system level. Corresponds to <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt> <tt class="docutils literal"><span class="pre">ETIMEDOUT</span></tt>.
 */
public class TimeoutError extends OSError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new TimeoutError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public TimeoutError(Object... args) {
        super(args);
    }

    /**
     * Creates a new TimeoutError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public TimeoutError(String message) {
        super(message);
    }

    @Override
    public TimeoutError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
