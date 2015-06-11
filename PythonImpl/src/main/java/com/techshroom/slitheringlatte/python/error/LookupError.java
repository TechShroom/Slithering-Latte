package com.techshroom.slitheringlatte.python.error;

/**
 * The base class for the exceptions that are raised when a key or index used on
 * a mapping or sequence is invalid: {@link IndexError}, {@link KeyError}. This
 * can be raised directly by <a class="reference internal"
 * href="https://docs.python.org/3/library/codecs.html#codecs.lookup"
 * title="codecs.lookup">
 * <tt class="xref py py-func docutils literal"><span class="pre">codecs.lookup()</span></tt>
 * </a>.
 */
public class LookupError extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new LookupError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public LookupError(Object... args) {
        super(args);
    }

    /**
     * Creates a new LookupError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public LookupError(String message) {
        super(message);
    }

    @Override
    public LookupError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
