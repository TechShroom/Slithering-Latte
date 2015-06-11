package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a given process doesn’t exist. Corresponds to
 * <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt>
 * <tt class="docutils literal"><span class="pre">ESRCH</span></tt>.
 */
public class ProcessLookupError extends OSError {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ProcessLookupError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public ProcessLookupError(Object... args) {
        super(args);
    }

    /**
     * Creates a new ProcessLookupError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public ProcessLookupError(String message) {
        super(message);
    }

    @Override
    public ProcessLookupError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
