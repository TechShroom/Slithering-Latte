package com.techshroom.slitheringlatte.python.underscore;

/**
 * Interface marker for __mul__'s Java equivalent, {@link #mul(Object o)}.
 * 
 * @author Kenzie Togami
 */
public interface MulSupported {
    static Object mul(Object self, Object other) {
        if (self instanceof MulSupported) {
            return ((MulSupported) self).mul(other);
        } else {
            throw new UnsupportedOperationException(self + " does not support mul");
        }
    }

    Object mul(Object other);
}
