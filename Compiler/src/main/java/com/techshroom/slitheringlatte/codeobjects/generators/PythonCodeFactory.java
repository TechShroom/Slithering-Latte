package com.techshroom.slitheringlatte.codeobjects.generators;

import static com.google.common.base.Preconditions.checkNotNull;

import com.techshroom.slitheringlatte.Options;
import com.techshroom.slitheringlatte.array.SingleArray;
import com.techshroom.slitheringlatte.codeobjects.PythonCodeContainer;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Factory for generating a PythonCodeContainer.
 * 
 * @author Kenzie Togami
 */
public interface PythonCodeFactory {

    /**
     * Creates a new array of containers from the given string.
     * 
     * @param descriptor
     *            - a descriptor, either {@link Options#STREAM} or a
     *            file/directory list separated by {@link File#pathSeparator}.
     * @return the new code containers
     */
    default PythonCodeContainer[] fromStringDescriptor(String descriptor) {
        checkNotNull(descriptor);
        if (descriptor.equals(Options.STREAM)) {
            return SingleArray.of(fromStream(), PythonCodeContainer.class);
        } else {
            return fromStringDescriptors(descriptor.split(Pattern
                    .quote(File.pathSeparator)));
        }
    }

    /**
     * Creates a new code container from the standard input stream.
     * 
     * @return the new code container
     */
    PythonCodeContainer fromStream();

    /**
     * Creates a new array of containers from the given strings.
     * 
     * @param descriptors
     *            - a list of files and directories
     * @return the new code containers
     */
    PythonCodeContainer[] fromStringDescriptors(String... descriptors);
}
