package com.techshroom.slitheringlatte.python.interfaces.collections.abcs;

/**
 * ABC for a class that provides the method {@link #call(Object[])}.
 * 
 * @author Kenzie Togami
 */
public interface PythonCallable {

    /**
     * Called when the instance is “called” as a function; if this method is
     * defined, {@code x(arg1, arg2, ...)} is a shorthand for
     * {@code x.__call__(arg1, arg2,
     * ...)}.
     * 
     * @param args
     *            - The arguments to pass to the function
     * @return The call result, which may be None
     */
    Object call(Object... args);

}
