package com.techshroom.slitheringlatte.python.error;

/**
 * Base class for warnings related to Unicode.
 */
public class UnicodeWarning extends Warning {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new UnicodeWarning with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public UnicodeWarning(Object... args) {
        super(args);
    }

    /**
     * Creates a new UnicodeWarning with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public UnicodeWarning(String message) {
        super(message);
    }

    @Override
    public UnicodeWarning with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
