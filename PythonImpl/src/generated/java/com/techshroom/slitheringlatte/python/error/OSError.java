package com.techshroom.slitheringlatte.python.error;

/**
 * This exception is raised when a system function returns a system-related error, including I/O failures such as “file not found” or “disk full” (not for illegal argument types or other incidental errors). Often a subclass of {@link OSError} will actually be raised as described in {@link } below. The <a class="reference internal" href="https://docs.python.org/3/library/errno.html#module-errno" title="errno: Standard errno system symbols."><tt class="xref py py-attr docutils literal"><span class="pre">errno</span></tt></a> attribute is a numeric error code from the C variable <tt class="xref c c-data docutils literal"><span class="pre">errno</span></tt>.<p>Under Windows, the <tt class="xref py py-attr docutils literal"><span class="pre">winerror</span></tt> attribute gives you the native Windows error code. The <a class="reference internal" href="https://docs.python.org/3/library/errno.html#module-errno" title="errno: Standard errno system symbols."><tt class="xref py py-attr docutils literal"><span class="pre">errno</span></tt></a> attribute is then an approximate translation, in POSIX terms, of that native error code.</p> 
 * <p>Under all platforms, the <tt class="xref py py-attr docutils literal"><span class="pre">strerror</span></tt> attribute is the corresponding error message as provided by the operating system (as formatted by the C functions <tt class="xref c c-func docutils literal"><span class="pre">perror()</span></tt> under POSIX, and <tt class="xref c c-func docutils literal"><span class="pre">FormatMessage()</span></tt> Windows).</p> 
 * <p>For exceptions that involve a file system path (such as <a class="reference internal" href="https://docs.python.org/3/library/functions.html#open" title="open"><tt class="xref py py-func docutils literal"><span class="pre">open()</span></tt></a> or <a class="reference internal" href="https://docs.python.org/3/library/os.html#os.unlink" title="os.unlink"><tt class="xref py py-func docutils literal"><span class="pre">os.unlink()</span></tt></a>), the exception instance will contain an additional attribute, <tt class="xref py py-attr docutils literal"><span class="pre">filename</span></tt>, which is the file name passed to the function. For functions that involve two file system paths (such as <a class="reference internal" href="https://docs.python.org/3/library/os.html#os.rename" title="os.rename"><tt class="xref py py-func docutils literal"><span class="pre">os.rename()</span></tt></a>), the exception instance will contain a second <tt class="xref py py-attr docutils literal"><span class="pre">filename2</span></tt> attribute corresponding to the second file name passed to the function.</p> 
 * <div class="versionchanged"> 
 *  <p><span class="versionmodified">Changed in version 3.3: </span>{@link EnvironmentError}, {@link IOError}, {@link WindowsError}, <tt class="xref py py-exc docutils literal"><span class="pre">VMSError</span></tt>, <a class="reference internal" href="https://docs.python.org/3/library/socket.html#socket.error" title="socket.error"><tt class="xref py py-exc docutils literal"><span class="pre">socket.error</span></tt></a>, <a class="reference internal" href="https://docs.python.org/3/library/select.html#select.error" title="select.error"><tt class="xref py py-exc docutils literal"><span class="pre">select.error</span></tt></a> and <tt class="xref py py-exc docutils literal"><span class="pre">mmap.error</span></tt> have been merged into {@link OSError}.</p> 
 * </div> 
 * <div class="versionchanged"> 
 *  <p><span class="versionmodified">Changed in version 3.4: </span>The <tt class="xref py py-attr docutils literal"><span class="pre">filename</span></tt> attribute is now the original file name passed to the function, instead of the name encoded to or decoded from the filesystem encoding. Also, the <tt class="xref py py-attr docutils literal"><span class="pre">filename2</span></tt> attribute was added.</p> 
 * </div>
 */
public class OSError extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new OSError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public OSError(Object... args) {
        super(args);
    }

    /**
     * Creates a new OSError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public OSError(String message) {
        super(message);
    }

    @Override
    public OSError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
