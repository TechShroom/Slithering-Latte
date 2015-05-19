package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a directory operation (such as <a class="reference internal"
 * href="https://docs.python.org/3/library/os.html#os.listdir"
 * title="os.listdir">
 * <tt class="xref py py-func docutils literal"><span class="pre">os.listdir()</span></tt>
 * </a>) is requested on something which is not a directory. Corresponds to
 * <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt>
 * <tt class="docutils literal"><span class="pre">ENOTDIR</span></tt>.
 */
public class NotADirectoryError extends OSError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new NotADirectoryError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public NotADirectoryError(Object... args) {
        super(args);
    }

    /**
     * Creates a new NotADirectoryError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public NotADirectoryError(String message) {
        super(message);
    }

    @Override
    public NotADirectoryError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
