package com.techshroom.slitheringlatte.codeobjects.generators;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.techshroom.slitheringlatte.EmptyArray;
import com.techshroom.slitheringlatte.codeobjects.PythonCodeContainer;

/**
 * Implementation of PythonCodeFactory.
 * 
 * @author Kenzie Togami
 */
public class PythonCodeFactoryImpl implements PythonCodeFactory {
    private static final EmptyArray<PythonCodeContainer> PCC_EMPTY_ARRAY =
            EmptyArray.of(PythonCodeContainer.class);

    @Override
    public PythonCodeContainer fromStream() {
        return new StreamPythonCodeContainer(new InputStreamReader(System.in));
    }

    @Override
    public PythonCodeContainer[] fromStringDescriptors(String... descriptors) {
        List<PythonCodeContainer> containers =
                Lists.newArrayListWithCapacity(descriptors.length);
        for (String s : descriptors) {
            try {
                containers.add(new FilePythonCodeContainer(s));
            } catch (FileNotFoundException e) {
                Throwables.propagate(e);
            }
        }
        return containers.toArray(PCC_EMPTY_ARRAY.get());
    }
}
