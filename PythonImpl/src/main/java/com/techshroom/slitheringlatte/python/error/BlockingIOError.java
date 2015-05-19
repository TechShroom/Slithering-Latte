package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when an operation would block on an object (e.g. socket) set for
 * non-blocking operation. Corresponds to
 * <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt>
 * <tt class="docutils literal"><span class="pre">EAGAIN</span></tt>,
 * <tt class="docutils literal"><span class="pre">EALREADY</span></tt>,
 * <tt class="docutils literal"><span class="pre">EWOULDBLOCK</span></tt> and
 * <tt class="docutils literal"><span class="pre">EINPROGRESS</span></tt>.
 * <p>
 * In addition to those of {@link OSError}, {@link BlockingIOError} can have one
 * more attribute:
 * </p>
 * <dl class="attribute">
 * <dt id="BlockingIOError.characters_written">
 * <tt class="descname">characters_written</tt> <a class="headerlink" href=
 * "https://docs.python.org/3/library/exceptions.html#BlockingIOError.characters_written"
 * title="Permalink to this definition">¶</a></dt>
 * <dd>
 * <p>
 * An integer containing the number of characters written to the stream before
 * it blocked. This attribute is available when using the buffered I/O classes
 * from the <a class="reference internal"
 * href="https://docs.python.org/3/library/io.html#module-io"
 * title="io: Core tools for working with streams.">
 * <tt class="xref py py-mod docutils literal"><span class="pre">io</span></tt>
 * </a> module.
 * </p>
 * </dd>
 * </dl>
 */
public class BlockingIOError extends OSError {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new BlockingIOError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public BlockingIOError(Object... args) {
        super(args);
    }

    /**
     * Creates a new BlockingIOError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public BlockingIOError(String message) {
        super(message);
    }

    @Override
    public BlockingIOError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
