package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when trying to create a file or directory which already exists.
 * Corresponds to
 * <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt>
 * <tt class="docutils literal"><span class="pre">EEXIST</span></tt>.
 */
public class FileExistsError extends OSError {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new FileExistsError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public FileExistsError(Object... args) {
        super(args);
    }

    /**
     * Creates a new FileExistsError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public FileExistsError(String message) {
        super(message);
    }

    @Override
    public FileExistsError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
