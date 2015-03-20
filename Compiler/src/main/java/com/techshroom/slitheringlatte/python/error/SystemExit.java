package com.techshroom.slitheringlatte.python.error;

/**
 * This exception is raised by the <a class="reference internal" href="https://docs.python.org/3/library/sys.html#sys.exit" title="sys.exit"><tt class="xref py py-func docutils literal"><span class="pre">sys.exit()</span></tt></a> function. When it is not handled, the Python interpreter exits; no stack traceback is printed. If the associated value is an integer, it specifies the system exit status (passed to C’s <tt class="xref c c-func docutils literal"><span class="pre">exit()</span></tt> function); if it is <tt class="docutils literal"><span class="pre">None</span></tt>, the exit status is zero; if it has another type (such as a string), the object’s value is printed and the exit status is one.<p>Instances have an attribute <tt class="xref py py-attr docutils literal"><span class="pre">code</span></tt> which is set to the proposed exit status or error message (defaulting to <tt class="docutils literal"><span class="pre">None</span></tt>). Also, this exception derives directly from {@link BaseException} and not {@link Exception}, since it is not technically an error.</p> 
 * <p>A call to <a class="reference internal" href="https://docs.python.org/3/library/sys.html#sys.exit" title="sys.exit"><tt class="xref py py-func docutils literal"><span class="pre">sys.exit()</span></tt></a> is translated into an exception so that clean-up handlers (<a class="reference internal" href="https://docs.python.org/3/reference/compound_stmts.html#finally"><tt class="xref std std-keyword docutils literal"><span class="pre">finally</span></tt></a> clauses of <a class="reference internal" href="https://docs.python.org/3/reference/compound_stmts.html#try"><tt class="xref std std-keyword docutils literal"><span class="pre">try</span></tt></a> statements) can be executed, and so that a debugger can execute a script without running the risk of losing control. The <a class="reference internal" href="https://docs.python.org/3/library/os.html#os._exit" title="os._exit"><tt class="xref py py-func docutils literal"><span class="pre">os._exit()</span></tt></a> function can be used if it is absolutely positively necessary to exit immediately (for example, in the child process after a call to <a class="reference internal" href="https://docs.python.org/3/library/os.html#os.fork" title="os.fork"><tt class="xref py py-func docutils literal"><span class="pre">os.fork()</span></tt></a>).</p> 
 * <p>The exception inherits from {@link BaseException} instead of {@link Exception} so that it is not accidentally caught by code that catches {@link Exception}. This allows the exception to properly propagate up and cause the interpreter to exit.</p>
 */
public class SystemExit extends BaseException {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new SystemExit with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public SystemExit(Object... args) {
        super(args);
    }

    /**
     * Creates a new SystemExit with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public SystemExit(String message) {
        super(message);
    }

    @Override
    public SystemExit with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
