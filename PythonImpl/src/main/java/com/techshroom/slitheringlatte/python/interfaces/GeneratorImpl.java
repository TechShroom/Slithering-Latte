package com.techshroom.slitheringlatte.python.interfaces;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import com.techshroom.slitheringlatte.python.error.GeneratorExit;
import com.techshroom.slitheringlatte.python.error.RuntimeError;
import com.techshroom.slitheringlatte.python.error.StopIteration;

/**
 * Implementation of Generator. Very broken.
 *
 * @param <T>
 *            - The type of the generated objects
 */
public final class GeneratorImpl<T> implements Generator<T>,
		Generator.YieldProvider<T> {

	private static final ExecutorService generatorRunner = Executors
			.newSingleThreadExecutor();

	private static <T> T waitActionUnwait(Runnable doThis, Callable<T> action,
			Runnable doThisAfter) {
		doThis.run();
		T res;
		try {
			res = action.call();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		} finally {
			doThisAfter.run();
		}
		return res;
	}

	private static <T extends Enum<T>> void atomicItemRemoval(
			AtomicReference<Set<T>> set, T item) {
		set.getAndUpdate(old -> {
			Set<T> copy = EnumSet.copyOf(old);
			copy.remove(item);
			return copy;
		});
	}

	private static <T extends Enum<T>> void atomicItemAddition(
			AtomicReference<Set<T>> set, T item) {
		set.getAndUpdate(old -> {
			Set<T> copy = EnumSet.copyOf(old);
			copy.add(item);
			return copy;
		});
	}

	private static enum MainThreadState {
		RUNNING, WAITING_FOR_VALUE, HAS_NEXT, EXITED;
	}

	private static enum GeneratorFuncState {
		MAIN_ACK, WAITING_TO_CONTINUE, THROW_GENERATOR_EXIT, EXITED_OR_ERRORED;
	}

	private final Consumer<Generator.YieldProvider<T>> genFunc;
	private final AtomicReference<Set<MainThreadState>> mainThreadState = new AtomicReference<>(
			EnumSet.of(MainThreadState.HAS_NEXT));
	private final AtomicReference<Set<GeneratorFuncState>> genFuncState = new AtomicReference<>(
			EnumSet.noneOf(GeneratorFuncState.class));
	private final Semaphore changeDetector = new Semaphore(1);
	{
		// immediately lock down the Semaphore to 0
		this.changeDetector.acquireUninterruptibly();
	}
	private boolean hasNextInvoked = false;
	private T generated;
	private Optional<Object> sendObj = Optional.empty();
	private Optional<RuntimeException> exceptionObj = Optional.empty();
	private Optional<Throwable> propogatingException = Optional.empty();

	GeneratorImpl(Consumer<Generator.YieldProvider<T>> genFunc) {
		System.err.println(Thread.currentThread());
		this.genFunc = wrap(genFunc);
	}

	private Consumer<Generator.YieldProvider<T>> wrap(
			Consumer<Generator.YieldProvider<T>> original) {
		return yielder -> {
			atomicItemAddition(this.mainThreadState, MainThreadState.RUNNING);
			notifyOfChange();
			// need to wait for main to get it's state setup
			System.err.println("WAIT FOR ACK");
			while (!this.genFuncState.get().contains(GeneratorFuncState.MAIN_ACK)) {
				waitForChange();
			}
			atomicItemRemoval(this.genFuncState, GeneratorFuncState.MAIN_ACK);
			try {
				original.accept(yielder);
			} catch (StopIteration | GeneratorExit stop) {
			} catch (Throwable e) {
				this.propogatingException = Optional.of(e);
			} finally {
				// either the generator function exited
				// or threw an exception
				this.mainThreadState.set(EnumSet.of(MainThreadState.EXITED));
				this.genFuncState.set(EnumSet
						.of(GeneratorFuncState.EXITED_OR_ERRORED));
				notifyOfChange();
				System.err.println("wrapped yielder exited completely");
			}
		};
	}

	private void waitForChange() {
		StackTraceElement[] stack = new Throwable().getStackTrace();
		// System.err.println("WAITING FOR CHANGE: " + stack[1]);
		this.changeDetector.acquireUninterruptibly();
	}

	private void notifyOfChange() {
		StackTraceElement[] stack = new Throwable().getStackTrace();
		// System.err.println("RELEASING CHANGE: " + stack[1]);
		this.changeDetector.release();
	}

	private boolean ensureRunning() {
		if (!this.mainThreadState.get().contains(MainThreadState.RUNNING)) {
			generatorRunner.execute(() -> this.genFunc.accept(this));
			System.err.println("just waitin for a signal");
			while (!this.mainThreadState.get()
					.contains(MainThreadState.RUNNING)) {
				waitForChange();
			}
			return true;
		}
		return false;
	}
	
	private void ack() {
		atomicItemAddition(this.genFuncState, GeneratorFuncState.MAIN_ACK);
		notifyOfChange();
	}

	private T next(Callable<T> action) {
		checkNotNull(action);
		if (ensureRunning()) {
			ack();
		}
		try {
			Callable<T> newAction = () -> {
				this.propogatingException.ifPresent(Throwables::propagate);
				return action.call();
			};
			Runnable letFuncContinue = () -> {
				atomicItemRemoval(this.genFuncState,
						GeneratorFuncState.WAITING_TO_CONTINUE);
				System.err.println("LET_CONTINUE_OK");
				notifyOfChange();
			};
			if (this.hasNextInvoked || !this.hasNext()) {
				T res = newAction.call();
				letFuncContinue.run();
				return res;
			} else {
				return waitActionUnwait(this::waitForValue, newAction,
						letFuncContinue);
			}
		} catch (Throwable e) {
			throw Throwables.propagate(e);
		} finally {
			this.hasNextInvoked = false;
		}
	}

	private void waitForValue() {
		StackTraceElement[] stack = new Throwable().getStackTrace();
		System.err.println("WAITING FOR VALUE: " + stack[1]);
		while (this.mainThreadState.get().contains(
				MainThreadState.WAITING_FOR_VALUE)) {
			// wait for a new value
			waitForChange();
		}
	}

	private void dumpInternalState() {
		System.err.println(MoreObjects.toStringHelper(this)
				.add("genState", this.genFuncState)
				.add("mainState", this.mainThreadState)
				.add("changeDetector", this.changeDetector)
				.add("hasNextInvoked", this.hasNextInvoked)
				.add("generated", this.generated).add("sendObj", this.sendObj)
				.add("exceptionObj", this.exceptionObj)
				.add("propogation", this.propogatingException));
	}

	@Override
	public boolean hasNext() {
		boolean sendACK = ensureRunning();
		this.hasNextInvoked = true;
		if (sendACK) {
			ack();
		}
		if (!this.mainThreadState.get().contains(MainThreadState.HAS_NEXT)) {
			return false;
		}
		waitForValue();
		// but DON'T allow the generator to continue
		// we don't want it to generate a new value yet
		return this.mainThreadState.get().contains(MainThreadState.HAS_NEXT);
	}

	@Override
	public T next() {
		return next(() -> this.generated);
	}

	@Override
	public T python$next() {
		dumpInternalState();
		if (!hasNext()) {
			this.propogatingException.ifPresent(Throwables::propagate);
		}
		return Generator.super.python$next();
	}

	@Override
	public T send(Object o) {
		return next(() -> {
			this.sendObj = Optional.of(o);
			return this.generated;
		});
	}

	@Override
	public T throwException(RuntimeException e) {
		return next(() -> {
			this.exceptionObj = Optional.of(e);
			return this.generated;
		});
	}

	@Override
	public void close() {
		boolean sendACK = ensureRunning();
		atomicItemAddition(this.genFuncState,
				GeneratorFuncState.THROW_GENERATOR_EXIT);
		atomicItemRemoval(this.genFuncState,
				GeneratorFuncState.WAITING_TO_CONTINUE);
		if (sendACK) {
			ack();
		} else {
			notifyOfChange();
		}
		T next = next();
		if (next != null) {
			throw new RuntimeError("generator ignored GeneratorExit");
		}
	}

	@Override
	public Object yield(T object) {
		System.err.println("yield start");
		this.generated = object;
		atomicItemRemoval(this.mainThreadState,
				MainThreadState.WAITING_FOR_VALUE);
		System.err.println("KILLED WAITING FOR VALUE");
		notifyOfChange();
		atomicItemAddition(this.genFuncState,
				GeneratorFuncState.WAITING_TO_CONTINUE);
		System.err.println("WAITING FOR CONTINUE");
		while (this.genFuncState.get().contains(
				GeneratorFuncState.WAITING_TO_CONTINUE)) {
			waitForChange();
		}
		System.err.println("CONTINUE");
		if (this.genFuncState.get().contains(
				GeneratorFuncState.THROW_GENERATOR_EXIT)) {
			// DON'T GIVE VALUE, THROW
			throw new GeneratorExit();
		}
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
