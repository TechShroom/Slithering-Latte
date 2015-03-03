package com.techshroom.slitheringlatte.python.underscore;

import com.techshroom.slitheringlatte.python.string.PythonLikeString;
import com.techshroom.slitheringlatte.python.string.PythonString;

/**
 * Interface marker for __str__'s Java equivalent, str().
 * 
 * @author Kenzie Togami
 */
public interface StrSupported {
    /**
     * Helper for converting {@link #toString()} to a PythonLikeString where
     * applicable, and otherwise using {@link #str()}.
     * 
     * @param o
     *            - the input object
     * @return the PythonLikeString that was retrieved from the object.
     */
    static PythonLikeString str(Object o) {
        if (o instanceof StrSupported) {
            return ((StrSupported) o).str();
        } else {
            return PythonString.wrapString(String.valueOf(o));
        }
    }

    default PythonLikeString str() {
        return PythonString.wrapString(this.toString());
    }
}
