package com.techshroom.slitheringlatte.python.interfaces;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import com.google.common.base.Throwables;
import com.techshroom.slitheringlatte.python.error.GeneratorExit;
import com.techshroom.slitheringlatte.python.error.RuntimeError;
import com.techshroom.slitheringlatte.python.error.StopIteration;

/**
 * Implementation of Generator.
 *
 * @param <T>
 *            - The type of the generated objects
 */
public final class GeneratorImpl<T> implements Generator<T>,
        Generator.YieldProvider<T> {

    private static final ExecutorService generatorRunner = Executors
            .newSingleThreadExecutor();

    private static <T> T waitActionUnwait(AtomicBoolean waitThis,
            Callable<T> action, AtomicBoolean unwaitThis) {
        doWait(waitThis);
        T res;
        try {
            res = action.call();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
        // this is not in finally because we don't want to let the generator go
        // when an exception is made
        undoWait(unwaitThis);
        return res;
    }

    private static void doWait(AtomicBoolean waiter) {
        try {
            waiter.set(true);
            waiter.wait();
        } catch (InterruptedException e) {
        }
    }

    private static void undoWait(AtomicBoolean waiter) {
        waiter.set(false);
        waiter.notify();
    }

    private final Consumer<Generator.YieldProvider<T>> genFunc;
    private final AtomicBoolean waitingForValue = new AtomicBoolean();
    private final AtomicBoolean waitingForContinue = new AtomicBoolean();
    private final AtomicBoolean hasNext = new AtomicBoolean(true);
    private final AtomicBoolean throwGeneratorExit = new AtomicBoolean();
    private boolean isRunning = false;
    private T generated;
    private Optional<Object> sendObj;
    private Optional<RuntimeException> exceptionObj;
    private Optional<Throwable> propogatingException;

    GeneratorImpl(Consumer<Generator.YieldProvider<T>> genFunc) {
        this.genFunc = wrap(genFunc);
    }

    private Consumer<Generator.YieldProvider<T>> wrap(
            Consumer<Generator.YieldProvider<T>> original) {
        return yielder -> {
            try {
                original.accept(yielder);
            } catch (StopIteration | GeneratorExit stop) {
            } catch (Throwable e) {
                this.propogatingException = Optional.of(e);
            } finally {
                // either the generator function exited
                // or threw an exception
                this.hasNext.set(false);
            }
        };
    }

    private void ensureRunning() {
        if (!this.isRunning) {
            generatorRunner.execute(() -> this.genFunc.accept(this));
            this.isRunning = true;
        }
    }

    private T next(Callable<T> action) {
        Callable<T> newAction = () -> {
            this.propogatingException.ifPresent(Throwables::propagate);
            return action.call();
        };
        return waitActionUnwait(this.waitingForValue,
                                newAction,
                                this.waitingForContinue);
    }

    @Override
    public boolean hasNext() {
        ensureRunning();
        // wait for a value
        doWait(this.waitingForValue);
        // but DON'T allow the generator to continue
        // we don't want it to generate a new value yet
        return this.hasNext.get();
    }

    @Override
    public T next() {
        ensureRunning();
        return next(() -> this.generated);
    }

    @Override
    public T send(Object o) {
        ensureRunning();
        return next(() -> {
            this.sendObj = Optional.of(o);
            return this.generated;
        });
    }

    @Override
    public T throwException(RuntimeException e) {
        ensureRunning();
        return next(() -> {
            this.exceptionObj = Optional.of(e);
            return this.generated;
        });
    }

    @Override
    public void close() {
        this.throwGeneratorExit.set(true);
        T next = next();
        if (next != null) {
            throw new RuntimeError("generator ignored GeneratorExit");
        }
    }

    @Override
    public Object yield(T object) {
        if (this.throwGeneratorExit.get()) {
            // DON'T GIVE VALUE, THROW
            throw new GeneratorExit();
        }
        this.generated =
                waitActionUnwait(this.waitingForContinue,
                                 () -> object,
                                 this.waitingForValue);
        if (this.sendObj.isPresent()) {
            Object ret = this.sendObj.get();
            this.sendObj = Optional.empty();
            return ret;
        } else if (this.exceptionObj.isPresent()) {
            RuntimeException e = this.exceptionObj.get();
            this.exceptionObj = Optional.empty();
            throw e;
        }
        return null;
    }

}
