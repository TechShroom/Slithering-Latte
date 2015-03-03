package com.techshroom.slitheringlatte.python.underscore;

import com.techshroom.slitheringlatte.python.error.TypeError;

/**
 * Interface marker for __len__'s Java equivalent, len().
 * 
 * @author Kenzie Togami
 */
public interface LenSupported {
    /**
     * 
     * @param o
     * @return
     */
    static int len(Object o) {
        if (o instanceof LenSupported) {
            return ((LenSupported) o).len();
        } else {
            return lengthNonSupported(o);
        }
    }

    /**
     * Length for non-len-supported objects. Also the default implementation of
     * {@link #len()}.
     * 
     * @param src
     *            - the object to get the length for
     * @return the length of the object
     * @throws TypeError
     *             if len() is not supported by the object
     */
    static int lengthNonSupported(Object src) throws TypeError {
        return LenGetters.getterFor(src).applyAsInt(src);
    }

    /**
     * @return
     */
    int len();
}
