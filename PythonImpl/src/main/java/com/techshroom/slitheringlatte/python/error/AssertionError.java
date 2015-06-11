package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when an <a class="reference internal"
 * href="https://docs.python.org/3/reference/simple_stmts.html#assert">
 * <tt class="xref std std-keyword docutils literal"><span class="pre">assert</span></tt>
 * </a> statement fails.
 */
public class AssertionError extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new AssertionError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public AssertionError(Object... args) {
        super(args);
    }

    /**
     * Creates a new AssertionError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public AssertionError(String message) {
        super(message);
    }

    @Override
    public AssertionError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
