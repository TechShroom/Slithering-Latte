package com.techshroom.slitheringlatte.python.error;

/**
 * Base class for warnings related to resource usage.<div class="versionadded">
 * <p>
 * <span class="versionmodified">New in version 3.2.</span>
 * </p>
 * </div>
 */
public class ResourceWarning extends Warning {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ResourceWarning with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public ResourceWarning(Object... args) {
        super(args);
    }

    /**
     * Creates a new ResourceWarning with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public ResourceWarning(String message) {
        super(message);
    }

    @Override
    public ResourceWarning with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
