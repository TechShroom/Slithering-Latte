package com.techshroom.slitheringlatte.python.error;

/**
 * Base class for warnings related to <a class="reference internal" href="https://docs.python.org/3/library/functions.html#bytes" title="bytes"><tt class="xref py py-class docutils literal"><span class="pre">bytes</span></tt></a> and <a class="reference internal" href="https://docs.python.org/3/library/functions.html#bytearray" title="bytearray"><tt class="xref py py-class docutils literal"><span class="pre">bytearray</span></tt></a>.
 */
public class BytesWarning extends Warning {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new BytesWarning with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public BytesWarning(Object... args) {
        super(args);
    }

    /**
     * Creates a new BytesWarning with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public BytesWarning(String message) {
        super(message);
    }

    @Override
    public BytesWarning with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
