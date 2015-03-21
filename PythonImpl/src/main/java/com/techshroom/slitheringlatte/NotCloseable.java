package com.techshroom.slitheringlatte;

import java.io.Closeable;

/**
 * Inverts Closeable.
 * 
 * @author Kenzie Togami
 */
public interface NotCloseable extends Closeable {
    @Override
    default void close() {
    }
}
