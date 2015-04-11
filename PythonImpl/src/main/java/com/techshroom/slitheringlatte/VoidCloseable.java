package com.techshroom.slitheringlatte;

import java.io.Closeable;

/**
 * Inverts Closeable.
 * 
 * @author Kenzie Togami
 */
public interface VoidCloseable extends Closeable {

    @Override
    default void close() {
    }

}
