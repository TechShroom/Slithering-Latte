package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a floating point operation fails. This exception is always
 * defined, but can only be raised when Python is configured with the
 * <tt class="docutils literal"><span class="pre">--with-fpectl</span></tt>
 * option, or the
 * <tt class="xref py py-const docutils literal"><span class="pre">WANT_SIGFPE_HANDLER</span></tt>
 * symbol is defined in the
 * <tt class="file docutils literal"><span class="pre">pyconfig.h</span></tt>
 * file.
 */
public class FloatingPointError extends ArithmeticError {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new FloatingPointError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public FloatingPointError(Object... args) {
        super(args);
    }

    /**
     * Creates a new FloatingPointError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public FloatingPointError(String message) {
        super(message);
    }

    @Override
    public FloatingPointError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
