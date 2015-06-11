package com.techshroom.slitheringlatte.python.interfaces.collections.abcs;

import com.techshroom.slitheringlatte.python.interfaces.generated.PythonObject;

/**
 * ABC for a class that provides the method {@link #length()}.
 * 
 * @author Kenzie Togami
 */
public interface PythonSized {

    interface Hint {

        int lengthHint();

    }

    /**
     * Called to implement the built-in function len(). Should return the length
     * of the object, an integer >= 0. Also, an object that doesn’t define a
     * {@link PythonObject#bool() bool()} method and whose {@link #length()}
     * method returns zero is considered to be false in a Boolean context.
     * 
     * @return The length of this object
     */
    int length();

}
