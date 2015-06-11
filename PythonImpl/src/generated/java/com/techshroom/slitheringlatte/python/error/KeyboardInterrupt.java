package com.techshroom.slitheringlatte.python.error;

/**
 * Raised when the user hits the interrupt key (normally <tt class="kbd docutils literal"><span class="pre">Control-C</span></tt> or <tt class="kbd docutils literal"><span class="pre">Delete</span></tt>). During execution, a check for interrupts is made regularly. The exception inherits from {@link BaseException} so as to not be accidentally caught by code that catches {@link Exception} and thus prevent the interpreter from exiting.
 */
public class KeyboardInterrupt extends BaseException {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new KeyboardInterrupt with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public KeyboardInterrupt(Object... args) {
        super(args);
    }

    /**
     * Creates a new KeyboardInterrupt with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public KeyboardInterrupt(String message) {
        super(message);
    }

    @Override
    public KeyboardInterrupt with_traceback(StackTraceElement[] tb) {
        super.with_traceback(tb);
        return this;
    }
}
