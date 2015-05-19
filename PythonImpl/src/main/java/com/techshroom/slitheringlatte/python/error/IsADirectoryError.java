package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a file operation (such as <a class="reference internal"
 * href="https://docs.python.org/3/library/os.html#os.remove" title="os.remove">
 * <tt class="xref py py-func docutils literal"><span class="pre">os.remove()</span></tt>
 * </a>) is requested on a directory. Corresponds to
 * <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt>
 * <tt class="docutils literal"><span class="pre">EISDIR</span></tt>.
 */
public class IsADirectoryError extends OSError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new IsADirectoryError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public IsADirectoryError(Object... args) {
        super(args);
    }

    /**
     * Creates a new IsADirectoryError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public IsADirectoryError(String message) {
        super(message);
    }

    @Override
    public IsADirectoryError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
