package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when an operation on a child process failed. Corresponds to <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt> <tt class="docutils literal"><span class="pre">ECHILD</span></tt>.
 */
public class ChildProcessError extends OSError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ChildProcessError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public ChildProcessError(Object... args) {
        super(args);
    }

    /**
     * Creates a new ChildProcessError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public ChildProcessError(String message) {
        super(message);
    }

    @Override
    public ChildProcessError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
