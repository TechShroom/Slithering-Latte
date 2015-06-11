package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when the <a class="reference internal" href="https://docs.python.org/3/library/functions.html#input" title="input"><tt class="xref py py-func docutils literal"><span class="pre">input()</span></tt></a> function hits an end-of-file condition (EOF) without reading any data. (N.B.: the <tt class="xref py py-meth docutils literal"><span class="pre">io.IOBase.read()</span></tt> and <a class="reference internal" href="https://docs.python.org/3/library/io.html#io.IOBase.readline" title="io.IOBase.readline"><tt class="xref py py-meth docutils literal"><span class="pre">io.IOBase.readline()</span></tt></a> methods return an empty string when they hit EOF.)
 */
public class EOFError extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new EOFError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public EOFError(Object... args) {
        super(args);
    }

    /**
     * Creates a new EOFError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public EOFError(String message) {
        super(message);
    }

    @Override
    public EOFError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
