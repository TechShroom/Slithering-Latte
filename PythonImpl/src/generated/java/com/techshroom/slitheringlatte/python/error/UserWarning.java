package com.techshroom.slitheringlatte.python.error;

/**
 * Base class for warnings generated by user code.
 */
public class UserWarning extends Warning {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new UserWarning with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public UserWarning(Object... args) {
        super(args);
    }

    /**
     * Creates a new UserWarning with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public UserWarning(String message) {
        super(message);
    }

    @Override
    public UserWarning with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}