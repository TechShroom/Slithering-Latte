package com.techshroom.slitheringlatte.python.underscore;

import com.techshroom.slitheringlatte.python.string.PythonLikeString;

/**
 * Interface marker for __repr__'s Java equivalent, repr().
 * 
 * @author Kenzie Togami
 */
public interface ReprSupported {
    /**
     * Collision interface for {@link #repr()} and {@link #str()}.
     * 
     * @author Kenzie Togami
     */
    interface ReprStrSupported extends ReprSupported, StrSupported {
        @Override
        default PythonLikeString repr() {
            return str();
        }
    }

    /**
     * Helper for getting the result of {@link #repr()}, even if the given
     * object doesn't implement it.
     * 
     * @param o
     *            - the input object
     * @return the PythonLikeString that was retrieved from the object.
     */
    static PythonLikeString repr(Object o) {
        if (o instanceof ReprSupported) {
            return ((ReprSupported) o).repr();
        } else {
            return StrSupported.str(o);
        }
    }

    /**
     * Return a string containing a printable representation of an object. This
     * is the same value yielded by conversions (reverse quotes). It is
     * sometimes useful to be able to access this operation as an ordinary
     * function. For many types, this function makes an attempt to return a
     * string that would yield an object with the same value when passed to
     * eval(), otherwise the representation is a string enclosed in angle
     * brackets that contains the name of the type of the object together with
     * additional information often including the name and address of the
     * object. A class can control what this function returns for its instances
     * by defining a repr() method. By default, this returns {@link #toString()}
     * .
     * 
     * @return a string containing a printable representation of an object
     */
    PythonLikeString repr();
}
