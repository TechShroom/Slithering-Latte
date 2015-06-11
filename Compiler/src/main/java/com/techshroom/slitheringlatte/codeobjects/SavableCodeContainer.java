package com.techshroom.slitheringlatte.codeobjects;

/**
 * Represents a savable code container.
 * 
 * @author Kenzie Togami
 */
public interface SavableCodeContainer extends CodeContainer {

    /**
     * Save the code currently contained.
     * 
     * @return {@code true} if the code was saved, {@code false} otherwise.
     */
    boolean save();
}
