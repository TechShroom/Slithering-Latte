package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when an operation runs out of memory but the situation may still be
 * rescued (by deleting some objects). The associated value is a string
 * indicating what kind of (internal) operation ran out of memory. Note that
 * because of the underlying memory management architecture (C’s
 * <tt class="xref c c-func docutils literal"><span class="pre">malloc()</span></tt>
 * function), the interpreter may not always be able to completely recover from
 * this situation; it nevertheless raises an exception so that a stack traceback
 * can be printed, in case a run-away program was the cause.
 */
public class MemoryError extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new MemoryError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public MemoryError(Object... args) {
        super(args);
    }

    /**
     * Creates a new MemoryError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public MemoryError(String message) {
        super(message);
    }

    @Override
    public MemoryError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
