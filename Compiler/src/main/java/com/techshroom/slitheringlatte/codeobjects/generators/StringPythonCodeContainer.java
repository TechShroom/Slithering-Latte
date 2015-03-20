package com.techshroom.slitheringlatte.codeobjects.generators;

import java.util.Collection;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.techshroom.slitheringlatte.NotCloseable;
import com.techshroom.slitheringlatte.codeobjects.PythonCodeContainer;

/**
 * Simple wrapper for Java lines of code.
 * 
 * @author Kenzie Togami
 */
@AutoValue
public abstract class StringPythonCodeContainer implements PythonCodeContainer,
        NotCloseable {
    /**
     * Wrap the given lines in a code container. Please use
     * {@link CodeFactory#wrap(Collection, com.techshroom.slitheringlatte.codeobjects.Language)}
     * or similar.
     * 
     * @param lines
     *            - collection of lines
     * @return a code container containing the given lines
     */
    public static StringPythonCodeContainer wrap(Collection<String> lines) {
        return new AutoValue_StringPythonCodeContainer(
                ImmutableList.copyOf(lines));
    }

    StringPythonCodeContainer() {
    }
}
