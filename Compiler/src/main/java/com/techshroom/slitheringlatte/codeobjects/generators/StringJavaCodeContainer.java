package com.techshroom.slitheringlatte.codeobjects.generators;

import java.util.Collection;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.techshroom.slitheringlatte.VoidCloseable;
import com.techshroom.slitheringlatte.codeobjects.JavaCodeContainer;

/**
 * Simple wrapper for Java lines of code.
 * 
 * @author Kenzie Togami
 */
@AutoValue
public abstract class StringJavaCodeContainer implements JavaCodeContainer,
        VoidCloseable {

    /**
     * Wrap the given lines in a code container. Please use
     * {@link CodeFactory#wrap(Collection, com.techshroom.slitheringlatte.codeobjects.Language)}
     * or similar.
     * 
     * @param lines
     *            - collection of lines
     * @return a code container containing the given lines
     */
    public static StringJavaCodeContainer wrap(Collection<String> lines) {
        return new AutoValue_StringJavaCodeContainer(
                ImmutableList.copyOf(lines));
    }

    StringJavaCodeContainer() {
    }
}
