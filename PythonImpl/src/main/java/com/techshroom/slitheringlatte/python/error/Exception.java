package com.techshroom.slitheringlatte.python.error;

/**
 * All built-in, non-system-exiting exceptions are derived from this class. All
 * user-defined exceptions should also be derived from this class.
 */
public class Exception extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new Exception with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public Exception(Object... args) {
        super(args);
    }

    /**
     * Creates a new Exception with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public Exception(String message) {
        super(message);
    }

    @Override
    public Exception with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
