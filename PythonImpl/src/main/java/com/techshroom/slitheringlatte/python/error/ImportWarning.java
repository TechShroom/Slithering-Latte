package com.techshroom.slitheringlatte.python.error;

/**
 * Base class for warnings about probable mistakes in module imports.
 */
public class ImportWarning extends Warning {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ImportWarning with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public ImportWarning(Object... args) {
        super(args);
    }

    /**
     * Creates a new ImportWarning with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public ImportWarning(String message) {
        super(message);
    }

    @Override
    public ImportWarning with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
