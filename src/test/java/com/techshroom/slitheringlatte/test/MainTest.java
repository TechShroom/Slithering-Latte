package com.techshroom.slitheringlatte.test;

import org.junit.Test;

import com.techshroom.slitheringlatte.Main;

/**
 * Just the main compile test.
 * 
 * @author Kenzie Togami
 */
public class MainTest {
    /**
     * Check that we can compile Python to Java.
     * 
     * @throws Exception
     *             exceptions propagate
     */
    @Test
    public void compilesPython() throws Exception {
        Main.main("-", "-");
    }
}
