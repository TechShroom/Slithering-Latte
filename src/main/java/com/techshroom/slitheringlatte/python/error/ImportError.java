package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when an <a class="reference internal" href="https://docs.python.org/3/reference/simple_stmts.html#import"><tt class="xref std std-keyword docutils literal"><span class="pre">import</span></tt></a> statement fails to find the module definition or when a <tt class="docutils literal"><span class="pre">from</span> <span class="pre">...</span> <span class="pre">import</span></tt> fails to find a name that is to be imported.<p>The <tt class="xref py py-attr docutils literal"><span class="pre">name</span></tt> and <tt class="xref py py-attr docutils literal"><span class="pre">path</span></tt> attributes can be set using keyword-only arguments to the constructor. When set they represent the name of the module that was attempted to be imported and the path to any file which triggered the exception, respectively.</p> 
 * <div class="versionchanged"> 
 *  <p><span class="versionmodified">Changed in version 3.3: </span>Added the <tt class="xref py py-attr docutils literal"><span class="pre">name</span></tt> and <tt class="xref py py-attr docutils literal"><span class="pre">path</span></tt> attributes.</p> 
 * </div>
 */
public class ImportError extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ImportError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public ImportError(Object... args) {
        super(args);
    }

    /**
     * Creates a new ImportError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public ImportError(String message) {
        super(message);
    }

    @Override
    public ImportError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
