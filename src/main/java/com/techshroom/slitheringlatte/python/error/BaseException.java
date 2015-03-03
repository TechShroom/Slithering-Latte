package com.techshroom.slitheringlatte.python.error;

import java.util.regex.Pattern;

import com.techshroom.slitheringlatte.python.Tuple;
import com.techshroom.slitheringlatte.python.string.PythonLikeString;
import com.techshroom.slitheringlatte.python.string.PythonString;
import com.techshroom.slitheringlatte.python.underscore.ReprSupported;
import com.techshroom.slitheringlatte.python.underscore.StrSupported;

/**
 * The base class for all built-in exceptions. It is not meant to be directly
 * inherited by user-defined classes (for that, use Exception). If
 * {@link #str()} is called on an instance of this class, the representation of
 * the argument(s) to the instance are returned, or the empty string when there
 * were no arguments.
 * 
 * @author Kenzie Togami
 */
public class BaseException
        extends RuntimeException implements StrSupported, ReprSupported {
    private static final long serialVersionUID = 1131936802208300547L;
    private static final Pattern DOT = Pattern.compile(".", Pattern.LITERAL);

    /**
     * Arguments, visible to more closely replicate Python.<br>
     * <br>
     * The tuple of arguments given to the exception constructor. Some built-in
     * exceptions (like {@link OSError}) expect a certain number of arguments and assign
     * a special meaning to the elements of this tuple, while others are usually
     * called only with a single string giving an error message.
     */
    public final Tuple args;
    
    private final String message;

    /**
     * Creates a new BaseException with the given arguments.
     * 
     * @param args
     *            - arguments
     */
    public BaseException(Object... args) {
        this.args = Tuple.create(args);
        if (this.args.size() == 1
                && this.args.get(0) instanceof PythonLikeString) {
            // message
            message = StrSupported.str(this.args.get(0)).toString();
        } else {
            message = null;
        }
    }

    /**
     * Creates a new BaseException with the given arguments.
     * 
     * @param message
     *            - the message. The arguments are also set to a tuple of the
     *            message
     */
    public BaseException(String message) {
        this.message = message;
        args = Tuple.create(message);
    }

    /**
     * This method sets tb as the new traceback for the exception and returns
     * the exception object.
     * 
     * @param tb
     *            - traceback
     * @return this
     */
    public BaseException with_traceback(StackTraceElement[] tb) {
        setStackTrace(tb);
        return this;
    }

    @Override
    public PythonLikeString repr() {
        return PythonString.wrapString(DOT.splitAsStream(getClass().getName())
                .reduce((id, current) -> current)
                .orElseThrow(() -> new IllegalStateException("no class name?"))
                + args);
    }

    @Override
    public String toString() {
        switch (args.len()) {
            case 0:
                return "";
            case 1:
                return String.valueOf(args.get(0));
            default:
                return StrSupported.str(args).toString();
        }
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
