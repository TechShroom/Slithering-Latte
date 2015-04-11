package com.techshroom.slitheringlatte.codeobjects;

import java.io.Closeable;
import java.util.Collection;
import java.util.Optional;

import com.techshroom.slitheringlatte.array.EmptyArray;

/**
 * A container for code.
 * 
 * @author Kenzie Togami
 */
public interface CodeContainer extends Closeable {
    /**
     * Get the language this code is.
     * 
     * @return a Language object represent this codes type.
     */
    Language<? extends CodeContainer> language();

    /**
     * Check if this container has a loading source.
     * 
     * @return {@code this instanceof }{@link LoadableCodeContainer}
     */
    default boolean isLoadable() {
        return this instanceof LoadableCodeContainer;
    }

    /**
     * Try to get this container as a loadable container.
     * 
     * @return an Optional of this container if it is loadable,
     *         {@link Optional#empty()} otherwise.
     */
    default Optional<LoadableCodeContainer> asLoadable() {
        return isLoadable() ? Optional.of((LoadableCodeContainer) this)
                           : Optional.empty();
    }

    /**
     * Check if this container has a saving target.
     * 
     * @return {@code this instanceof }{@link SavableCodeContainer}
     */
    default boolean isSavable() {
        return this instanceof SavableCodeContainer;
    }

    /**
     * Try to get this container as a savable container.
     * 
     * @return an Optional of this container if it is savable,
     *         {@link Optional#empty()} otherwise.
     */
    default Optional<SavableCodeContainer> asSavable() {
        return isSavable() ? Optional.of((SavableCodeContainer) this)
                          : Optional.empty();
    }

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
        return getLines().toArray(EmptyArray.STRING.getRegular().get());
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
