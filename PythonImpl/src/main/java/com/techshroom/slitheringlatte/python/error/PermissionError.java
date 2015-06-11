package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when trying to run an operation without the adequate access rights -
 * for example filesystem permissions. Corresponds to
 * <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt>
 * <tt class="docutils literal"><span class="pre">EACCES</span></tt> and
 * <tt class="docutils literal"><span class="pre">EPERM</span></tt>.
 */
public class PermissionError extends OSError {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new PermissionError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public PermissionError(Object... args) {
        super(args);
    }

    /**
     * Creates a new PermissionError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public PermissionError(String message) {
        super(message);
    }

    @Override
    public PermissionError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
