package com.techshroom.slitheringlatte.codeobjects.generators;

import com.techshroom.slitheringlatte.codeobjects.PythonCodeContainer;
import com.techshroom.slitheringlatte.Options;

/**
 * Factory for generating a PythonCodeContainer.
 * 
 * @author Kenzie Togami
 */
public interface PythonCodeFactory {
    /**
     * Creates a new container from the given string.
     * 
     * @param s
     *            - a descriptor, either {@link Options#STREAM} or a file
     * @return a new PythonCodeContainer
     */
    PythonCodeContainer fromStringDescriptor(String s);
}
