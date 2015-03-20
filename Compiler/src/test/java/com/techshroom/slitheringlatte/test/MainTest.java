package com.techshroom.slitheringlatte.test;

import java.io.InputStream;

import org.junit.Test;

import com.google.common.io.ByteSource;
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
        InputStream restore = System.in;
        System.setIn(ByteSource.wrap("".getBytes()).openBufferedStream());
        Main.main("-", "-");
        System.in.close();
        System.setIn(restore);
    }
}
