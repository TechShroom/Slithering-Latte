package com.techshroom.slitheringlatte.python.error;

/**
 * This exception is raised when a weak reference proxy, created by the <a
 * class="reference internal"
 * href="https://docs.python.org/3/library/weakref.html#weakref.proxy"
 * title="weakref.proxy">
 * <tt class="xref py py-func docutils literal"><span class="pre">weakref.proxy()</span></tt>
 * </a> function, is used to access an attribute of the referent after it has
 * been garbage collected. For more information on weak references, see the <a
 * class="reference internal"
 * href="https://docs.python.org/3/library/weakref.html#module-weakref"
 * title="weakref: Support for weak references and weak dictionaries.">
 * <tt class="xref py py-mod docutils literal"><span class="pre">weakref</span></tt>
 * </a> module.
 */
public class ReferenceError extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ReferenceError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public ReferenceError(Object... args) {
        super(args);
    }

    /**
     * Creates a new ReferenceError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public ReferenceError(String message) {
        super(message);
    }

    @Override
    public ReferenceError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
