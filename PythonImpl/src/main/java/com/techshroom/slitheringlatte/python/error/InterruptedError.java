package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a system call is interrupted by an incoming signal. Corresponds
 * to
 * <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt>
 * <tt class="docutils literal"><span class="pre">EINTR</span></tt>.
 */
public class InterruptedError extends OSError {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new InterruptedError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public InterruptedError(Object... args) {
        super(args);
    }

    /**
     * Creates a new InterruptedError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public InterruptedError(String message) {
        super(message);
    }

    @Override
    public InterruptedError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
