package com.techshroom.slitheringlatte.codeobjects.generators;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import com.techshroom.slitheringlatte.Options;
import com.techshroom.slitheringlatte.codeobjects.PythonCodeContainer;

/**
 * Implementation of PythonCodeFactory.
 * 
 * @author Kenzie Togami
 */
public class PythonCodeFactoryImpl implements PythonCodeFactory {
    @Override
    public PythonCodeContainer fromStringDescriptor(String s) {
        if (s.equals(Options.STREAM)) {
            return new StreamPythonCodeContainer(new InputStreamReader(
                    System.in));
        }
        try {
            return new FilePythonCodeContainer(s);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + s);
            if (Options.DEBUG) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
