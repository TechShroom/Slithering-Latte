package com.techshroom.slitheringlatte.python.underscore;

import com.techshroom.slitheringlatte.python.string.PythonLikeString;

/**
 * Interface marker for __doc__'s Java equivalent, doc(). One thing that is not
 * enforcable is the no subclass inheritance rule. In that case, if the subclass
 * is from this Python compile, the subclass will override {@link #doc()} with
 * its own documentation or return {@code null}.
 * 
 * @author Kenzie Togami
 */
public interface DocSupported {
    /**
     * Gets the documentation string.
     * 
     * @return The function’s documentation string, or {@code null} if
     *         unavailable
     */
    PythonLikeString doc();
}
