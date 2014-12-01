package com.techshroom.slitheringlatte.codeobjects;

import java.util.Collection;

import com.techshroom.slitheringlatte.EmptyArray;

/**
 * A container for code.
 * 
 * @author Kenzie Togami
 */
public interface CodeContainer {
    /**
     * Get the language this code is.
     * 
     * @return a Language object represent this codes type.
     */
    Language language();

    /**
     * Load the code into the container.
     */
    void load();

    /**
     * Save the code currently contained.
     * 
     * @return {@code true} if the code was saved, {@code false} otherwise.
     */
    boolean save();

    /**
     * Get all the lines of code in this container.
     * 
     * @return the lines of code as a Collection
     */
    Collection<String> getLines();

    /**
     * Get all the lines of code in this container.
     * 
     * @return the lines of code as a array of Strings
     */
    default String[] getLinesArray() {
        return getLines().toArray(EmptyArray.STRING);
    }

    /**
     * Get all the code in one String.
     * 
     * @return all of the code as one String
     */
    default String getAllCode() {
        return String.join("\n", getLines());
    }
}
