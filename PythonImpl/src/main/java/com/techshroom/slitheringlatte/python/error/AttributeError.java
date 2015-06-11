package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when an attribute reference (see <a class="reference internal" href=
 * "https://docs.python.org/3/reference/expressions.html#attribute-references">
 * <em>Attribute references</em></a>) or assignment fails. (When an object does
 * not support attribute references or attribute assignments at all,
 * {@link TypeError} is raised.)
 */
public class AttributeError extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new AttributeError with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public AttributeError(Object... args) {
        super(args);
    }

    /**
     * Creates a new AttributeError with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public AttributeError(String message) {
        super(message);
    }

    @Override
    public AttributeError with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
