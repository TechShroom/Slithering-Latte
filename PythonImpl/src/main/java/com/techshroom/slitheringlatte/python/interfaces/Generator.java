package com.techshroom.slitheringlatte.python.interfaces;

import java.util.function.Consumer;

import com.techshroom.slitheringlatte.python.error.GeneratorExit;
import com.techshroom.slitheringlatte.python.error.RuntimeError;
import com.techshroom.slitheringlatte.python.error.StopIteration;
import com.techshroom.slitheringlatte.python.interfaces.collections.abcs.PythonIterator;

/**
 * Generator object.
 * 
 * @author Kenzie Togami
 *
 * @param <T>
 *            - The type of the generated objects
 */
public interface Generator<T> extends PythonIterator<T> {

    /**
     * Provider for the {@link #yield(Object)} function, used to implement the
     * generator object.
     *
     * @param <T>
     *            - The type that is yielded by the generator function
     */
    interface YieldProvider<T> {

        Object yield(T object);

    }

    /**
     * Creates a new generator object that uses the given generator function.
     * 
     * @param genFunc
     *            - The function that will call yield on the given YieldProvider
     *            to generate objects
     * @return The new generator object
     */
    public static <T> Generator<T> newGenerator(
            Consumer<Generator.YieldProvider<T>> genFunc) {
        return new GeneratorImpl<T>(genFunc);
    }

    /**
     * Resumes the execution and “sends” a value into the generator function.
     * The value argument becomes the result of the current yield expression.
     * The {@link #send(Object)} method returns the next value yielded by the
     * generator, or raises StopIteration if the generator exits without
     * yielding another value. When {@link #send(Object)} is called to start the
     * generator, it must be called with None as the argument, because there is
     * no yield expression that could receive the value.
     * 
     * @param o
     *            - The object to send
     * @return The next object
     */
    T send(Object o);

    /**
     * Raises an exception at the point where the generator was paused, and
     * returns the next value yielded by the generator function. If the
     * generator exits without yielding another value, a {@link StopIteration}
     * exception is raised. If the generator function does not catch the
     * passed-in exception, or raises a different exception, then that exception
     * propagates to the caller.
     * 
     * @param e
     *            - The exception to throw
     * @return The next object
     */
    T throwException(RuntimeException e);

    /**
     * Raises a {@link GeneratorExit} at the point where the generator function
     * was paused. If the generator function then raises {@link StopIteration}
     * (by exiting normally, or due to already being closed) or
     * {@link GeneratorExit} (by not catching the exception), close returns to
     * its caller. If the generator yields a value, a {@link RuntimeError} is
     * raised. If the generator raises any other exception, it is propagated to
     * the caller. {@link #close()} does nothing if the generator has already
     * exited due to an exception or normal exit.
     */
    void close();

}
