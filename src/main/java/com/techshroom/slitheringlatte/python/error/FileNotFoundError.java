package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a file or directory is requested but doesn�t exist. Corresponds to <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt> <tt class="docutils literal"><span class="pre">ENOENT</span></tt>.
 */
public class FileNotFoundError extends OSError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new FileNotFoundError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public FileNotFoundError(Object... args) {
        super(args);
    }

    /**
     * Creates a new FileNotFoundError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public FileNotFoundError(String message) {
        super(message);
    }

    @Override
    public FileNotFoundError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
