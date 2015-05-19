package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when a Unicode-related encoding or decoding error occurs. It is a
 * subclass of {@link ValueError}.
 * <p>
 * {@link UnicodeError} has attributes that describe the encoding or decoding
 * error. For example,
 * <tt class="docutils literal"><span class="pre">err.object[err.start:err.end]</span></tt>
 * gives the particular invalid input that the codec failed on.
 * </p>
 * <dl class="attribute">
 * <dt id="UnicodeError.encoding">
 * <tt class="descname">encoding</tt> <a class="headerlink" href=
 * "https://docs.python.org/3/library/exceptions.html#UnicodeError.encoding"
 * title="Permalink to this definition">¶</a></dt>
 * <dd>
 * <p>
 * The name of the encoding that raised the error.
 * </p>
 * </dd>
 * </dl>
 * <dl class="attribute">
 * <dt id="UnicodeError.reason">
 * <tt class="descname">reason</tt> <a class="headerlink" href=
 * "https://docs.python.org/3/library/exceptions.html#UnicodeError.reason"
 * title="Permalink to this definition">¶</a></dt>
 * <dd>
 * <p>
 * A string describing the specific codec error.
 * </p>
 * </dd>
 * </dl>
 * <dl class="attribute">
 * <dt id="UnicodeError.object">
 * <tt class="descname">object</tt> <a class="headerlink" href=
 * "https://docs.python.org/3/library/exceptions.html#UnicodeError.object"
 * title="Permalink to this definition">¶</a></dt>
 * <dd>
 * <p>
 * The object the codec was attempting to encode or decode.
 * </p>
 * </dd>
 * </dl>
 * <dl class="attribute">
 * <dt id="UnicodeError.start">
 * <tt class="descname">start</tt> <a class="headerlink"
 * href="https://docs.python.org/3/library/exceptions.html#UnicodeError.start"
 * title="Permalink to this definition">¶</a></dt>
 * <dd>
 * <p>
 * The first index of invalid data in <a class="reference internal"
 * href="https://docs.python.org/3/library/functions.html#object"
 * title="object">
 * <tt class="xref py py-attr docutils literal"><span class="pre">object</span></tt>
 * </a>.
 * </p>
 * </dd>
 * </dl>
 * <dl class="attribute">
 * <dt id="UnicodeError.end">
 * <tt class="descname">end</tt> <a class="headerlink"
 * href="https://docs.python.org/3/library/exceptions.html#UnicodeError.end"
 * title="Permalink to this definition">¶</a></dt>
 * <dd>
 * <p>
 * The index after the last invalid data in <a class="reference internal"
 * href="https://docs.python.org/3/library/functions.html#object"
 * title="object">
 * <tt class="xref py py-attr docutils literal"><span class="pre">object</span></tt>
 * </a>.
 * </p>
 * </dd>
 * </dl>
 */
public class UnicodeError extends ValueError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new UnicodeError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public UnicodeError(Object... args) {
        super(args);
    }

    /**
     * Creates a new UnicodeError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public UnicodeError(String message) {
        super(message);
    }

    @Override
    public UnicodeError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
