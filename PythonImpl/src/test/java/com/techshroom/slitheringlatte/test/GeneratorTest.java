package com.techshroom.slitheringlatte.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.function.Consumer;

import org.junit.Ignore;
import org.junit.Test;

import com.techshroom.slitheringlatte.python.error.GeneratorExit;
import com.techshroom.slitheringlatte.python.error.RuntimeError;
import com.techshroom.slitheringlatte.python.error.StopIteration;
import com.techshroom.slitheringlatte.python.interfaces.Generator;

/**
 * Ensures that the generator object system works correctly.
 */
@Ignore // until not broken
public class GeneratorTest {

	@Test
	public void singleValueGeneratorNormal() {
		Consumer<Generator.YieldProvider<Boolean>> func = yielder -> yielder
				.yield(true);
		Generator<Boolean> gen = Generator.newGenerator(func);
		assertEquals(true, gen.python$next());
		try {
			gen.python$next();
			fail("StopIteration not thrown");
		} catch (StopIteration expected) {
			// TODO assert exception message?
		}
	}

	@Test
	public void singleValueGeneratorPropagate() {
		Consumer<Generator.YieldProvider<Boolean>> func = yielder -> {
			throw new AssertionError("propogated");
		};
		Generator<Boolean> gen = Generator.newGenerator(func);
		try {
			gen.python$next();
			fail("no exception not thrown");
		} catch (AssertionError expected) {
			assertEquals("propogated", expected.getMessage());
		}
	}

	@Test
	public void singleValueGeneratorNextThenPropagate() {
		Consumer<Generator.YieldProvider<Boolean>> func = yielder -> {
			yielder.yield(true);
			throw new AssertionError("propogated");
		};
		Generator<Boolean> gen = Generator.newGenerator(func);
		assertEquals(true, gen.python$next());
		try {
			gen.python$next();
			fail("no exception not thrown");
		} catch (AssertionError expected) {
			assertEquals("propogated", expected.getMessage());
		}
	}

	@Test
	public void singleValueGeneratorIgnoreGenExit() {
		Consumer<Generator.YieldProvider<Boolean>> func = yielder -> {
			try {
				yielder.yield(true);
			} catch (GeneratorExit ignored) {
				yielder.yield(false);
			}
		};
		Generator<Boolean> gen = Generator.newGenerator(func);
		try {
			gen.close();
			fail("no exception not thrown");
		} catch (RuntimeError expected) {
		}
	}

}
