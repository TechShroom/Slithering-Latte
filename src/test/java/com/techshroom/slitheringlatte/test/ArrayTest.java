package com.techshroom.slitheringlatte.test;

import static org.junit.Assert.*;

import com.techshroom.slitheringlatte.array.EmptyArray;
import com.techshroom.slitheringlatte.array.SingleArray;

import org.junit.Test;

/**
 * Array tests.
 * 
 * @author Kenzie Togami
 */
public class ArrayTest {
    /**
     * A call to {@link SingleArray#of(Object)} with a non-null object works.
     * 
     * @throws Exception
     *             exceptions propagate
     */
    @Test
    public void checkSingleArrayNonNull() throws Exception {
        assertArrayEquals(new Object[] { this }, SingleArray.of(this));
    }

    /**
     * A call to {@link SingleArray#of(Object)} with a null object fails.
     * 
     * @throws Exception
     *             exceptions propagate
     */
    @Test(expected = NullPointerException.class)
    public void checkSingleArrayNull() throws Exception {
        SingleArray.of(null);
    }

    /**
     * A call to {@link SingleArray#of(Object, Class)} with a non-null object
     * works.
     * 
     * @throws Exception
     *             exceptions propagate
     */
    @Test
    public void checkSingleArrayNonNullWithClass() throws Exception {
        assertArrayEquals(new Object[] { this },
                          SingleArray.of(this, Object.class));
    }

    /**
     * A call to {@link SingleArray#of(Object, Class)} with a null object works.
     * 
     * @throws Exception
     *             exceptions propagate
     */
    @Test
    public void checkSingleArrayNullWithClass() throws Exception {
        assertArrayEquals(new Object[] { null },
                          SingleArray.of(null, Object.class));
    }

    /**
     * Check that the EmptyArray class makes empty arrays.
     * 
     * @throws Exception
     *             exceptions propagate
     */
    @Test
    public void checkEmptyArray() throws Exception {
        assertArrayEquals(new Object[0], EmptyArray.of(Object.class).get());
        assertArrayEquals(new String[0], EmptyArray.STRING.get());
    }
}
