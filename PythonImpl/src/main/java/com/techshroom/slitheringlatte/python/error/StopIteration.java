package com.techshroom.slitheringlatte.python.error;

/**
 * Raised by built-in function <a class="reference internal"
 * href="https://docs.python.org/3/library/functions.html#next" title="next">
 * <tt class="xref py py-func docutils literal"><span class="pre">next()</span></tt>
 * </a> and an <a class="reference internal"
 * href="https://docs.python.org/3/glossary.html#term-iterator">
 * <em class="xref std std-term">iterator</em></a>‘s <a
 * class="reference internal"
 * href="https://docs.python.org/3/library/stdtypes.html#iterator.__next__"
 * title="iterator.__next__">
 * <tt class="xref py py-meth docutils literal"><span class="pre">__next__()</span></tt>
 * </a> method to signal that there are no further items produced by the
 * iterator.
 * <p>
 * The exception object has a single attribute
 * <tt class="xref py py-attr docutils literal"><span class="pre">value</span></tt>
 * , which is given as an argument when constructing the exception, and defaults
 * to <a class="reference internal"
 * href="https://docs.python.org/3/library/constants.html#None" title="None">
 * <tt class="xref py py-const docutils literal"><span class="pre">None</span></tt>
 * </a>.
 * </p>
 * <p>
 * When a generator function returns, a new {@link StopIteration} instance is
 * raised, and the value returned by the function is used as the
 * <tt class="xref py py-attr docutils literal"><span class="pre">value</span></tt>
 * parameter to the constructor of the exception.
 * </p>
 * <div class="versionchanged">
 * <p>
 * <span class="versionmodified">Changed in version 3.3: </span>Added
 * <tt class="docutils literal"><span class="pre">value</span></tt> attribute
 * and the ability for generator functions to use it to return a value.
 * </p>
 * </div>
 */
public class StopIteration extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new StopIteration with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public StopIteration(Object... args) {
        super(args);
    }

    /**
     * Creates a new StopIteration with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public StopIteration(String message) {
        super(message);
    }

    @Override
    public StopIteration with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
