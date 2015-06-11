package com.techshroom.slitheringlatte.codeobjects;

/**
 * Represents a loadable code container.
 * 
 * @author Kenzie Togami
 */
public interface LoadableCodeContainer extends CodeContainer {

    /**
     * Load the code into the container.
     * 
     * @return {@code true} if the code was load, {@code false} otherwise.
     */
    boolean load();
}
