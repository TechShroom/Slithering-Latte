package com.techshroom.slitheringlatte.python.interfaces;

import static com.techshroom.slitheringlatte.python.Builtins.None;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import com.google.common.base.Throwables;
import com.techshroom.slitheringlatte.logging.Log;
import com.techshroom.slitheringlatte.python.error.GeneratorExit;
import com.techshroom.slitheringlatte.python.error.RuntimeError;
import com.techshroom.slitheringlatte.python.error.StopIteration;
import com.techshroom.slitheringlatte.python.error.TypeError;

/**
 * Implementation of Generator. May break, report bugs.
 *
 * @param <T>
 *            - The type of the generated objects
 */
public final class GeneratorImpl<T> implements Generator<T>,
        Generator.YieldProvider<T> {

    private static final ExecutorService generatorRunner = Executors
            .newCachedThreadPool();

    private final Consumer<Generator.YieldProvider<T>> genFunc;
    private final AtomicBoolean running = new AtomicBoolean();
    private final AtomicBoolean aboutToExit = new AtomicBoolean();
    private volatile CompletableFuture<T> genToRequester =
            new CompletableFuture<>();
    private volatile CompletableFuture<Object> reqToGen =
            new CompletableFuture<>();
    private boolean hasNextGeneratedValue;
    private T generated;

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
                this.genToRequester.completeExceptionally(e);
            } finally {
                // either the generator function exited
                // or threw an exception
                this.aboutToExit.set(true);
                this.genToRequester.completeExceptionally(new StopIteration());
            }
        };
    }

    /**
     * @return {@code true} if the generator was just started
     */
    private boolean attemptStart() {
        synchronized (this.running) {
            if (!this.running.get() && !this.aboutToExit.get()) {
                generatorRunner.submit(() -> this.genFunc.accept(this));
                this.running.set(true);
                return true;
            }
        }
        return false;
    }

    private void resetGenToRequester() {
        this.genToRequester = new CompletableFuture<>();
    }

    private void resetRequesterToGen() {
        this.reqToGen = new CompletableFuture<>();
    }

    private T handleException(Exception ex) {
        if (ex instanceof ExecutionException) {
            // re-throw
            // TODO do we really want this to re-throw non-RuntimeExceptions?
            throw Throwables.propagate(ex.getCause());
        }
        Log log = Log.getDefaultLogImplementation();
        log.error("Unexpected exception:");
        log.logException(ex);
        return null;
    }

    private void throwStopIfDone() {
        if (this.aboutToExit.get()) {
            throw new StopIteration();
        }
    }

    @Override
    public boolean hasNext() {
        if (this.aboutToExit.get()) {
            return false;
        }
        if (this.hasNextGeneratedValue) {
            return true;
        }
        boolean justStarted = attemptStart();
        try {
            if (!justStarted) {
                this.reqToGen.complete(null);
            }
            this.generated = this.genToRequester.get();
            this.hasNextGeneratedValue = true;
            return true;
        } catch (InterruptedException | ExecutionException e) {
            handleException(e);
        } finally {
            resetGenToRequester();
        }
        return false;
    }

    @Override
    public T next() {
        throwStopIfDone();
        if (this.hasNextGeneratedValue) {
            this.hasNextGeneratedValue = false;
            return this.generated;
        }
        return send(None);
    }

    @Override
    public T send(Object o) {
        throwStopIfDone();
        boolean doComplete = true;
        if (!this.running.get()) {
            if (o != None) {
                throw new TypeError(
                        "can't send non-None value to a just-started generator");
            } else {
                doComplete = false;
                attemptStart();
            }
        }
        try {
            if (doComplete) {
                this.reqToGen.complete(o);
            }
            T val = this.genToRequester.get();
            return val;
        } catch (InterruptedException | ExecutionException e) {
            return handleException(e);
        } finally {
            resetGenToRequester();
        }
    }

    @Override
    public T throwException(RuntimeException e) {
        throwStopIfDone();
        if (!this.running.get()) {
            this.aboutToExit.set(true);
            throw e;
        }
        try {
            this.reqToGen.completeExceptionally(e);
            T val = this.genToRequester.get();
            return val;
        } catch (InterruptedException | ExecutionException ex) {
            return handleException(ex);
        } finally {
            resetGenToRequester();
        }
    }

    @Override
    public void close() {
        if (!this.running.get() || this.aboutToExit.get()) {
            return;
        }
        try {
            this.reqToGen.completeExceptionally(new GeneratorExit());
            if (this.genToRequester.get() != null) {
                throw new RuntimeError("generator ignored GeneratorExit");
            }
        } catch (InterruptedException | ExecutionException e) {
            handleException(e);
        } finally {
            resetGenToRequester();
        }
    }

    @Override
    public Object yield(T object) {
        this.genToRequester.complete(object);
        try {
            Object next = this.reqToGen.get();
            return next;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            Throwable actualException = e.getCause();
            if (actualException instanceof GeneratorExit) {
                throw (GeneratorExit) actualException;
            }
        } finally {
            resetRequesterToGen();
        }
        return null;
    }

}
