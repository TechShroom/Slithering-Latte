package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when the interpreter finds an internal error, but the situation does
 * not look so serious to cause it to abandon all hope. The associated value is
 * a string indicating what went wrong (in low-level terms).
 * <p>
 * You should report this to the author or maintainer of your Python
 * interpreter. Be sure to report the version of the Python interpreter (
 * <tt class="docutils literal"><span class="pre">sys.version</span></tt>; it is
 * also printed at the start of an interactive Python session), the exact error
 * message (the exception’s associated value) and if possible the source of the
 * program that triggered the error.
 * </p>
 */
public class SystemError extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new SystemError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public SystemError(Object... args) {
        super(args);
    }

    /**
     * Creates a new SystemError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public SystemError(String message) {
        super(message);
    }

    @Override
    public SystemError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
