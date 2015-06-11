package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when the parser encounters a syntax error. This may occur in an <a
 * class="reference internal"
 * href="https://docs.python.org/3/reference/simple_stmts.html#import">
 * <tt class="xref std std-keyword docutils literal"><span class="pre">import</span></tt>
 * </a> statement, in a call to the built-in functions <a
 * class="reference internal"
 * href="https://docs.python.org/3/library/functions.html#exec" title="exec">
 * <tt class="xref py py-func docutils literal"><span class="pre">exec()</span></tt>
 * </a> or <a class="reference internal"
 * href="https://docs.python.org/3/library/functions.html#eval" title="eval">
 * <tt class="xref py py-func docutils literal"><span class="pre">eval()</span></tt>
 * </a>, or when reading the initial script or standard input (also
 * interactively).
 * <p>
 * Instances of this class have attributes
 * <tt class="xref py py-attr docutils literal"><span class="pre">filename</span></tt>,
 * <tt class="xref py py-attr docutils literal"><span class="pre">lineno</span></tt>,
 * <tt class="xref py py-attr docutils literal"><span class="pre">offset</span></tt>
 * and
 * <tt class="xref py py-attr docutils literal"><span class="pre">text</span></tt>
 * for easier access to the details. <a class="reference internal"
 * href="https://docs.python.org/3/library/stdtypes.html#str" title="str">
 * <tt class="xref py py-func docutils literal"><span class="pre">str()</span></tt>
 * </a> of the exception instance returns only the message.
 * </p>
 */
public class SyntaxError extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new SyntaxError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public SyntaxError(Object... args) {
        super(args);
    }

    /**
     * Creates a new SyntaxError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public SyntaxError(String message) {
        super(message);
    }

    @Override
    public SyntaxError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
