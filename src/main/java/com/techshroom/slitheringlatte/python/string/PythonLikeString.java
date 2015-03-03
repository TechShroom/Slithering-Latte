package com.techshroom.slitheringlatte.python.string;

import java.math.BigInteger;
import java.util.Optional;

import com.techshroom.slitheringlatte.python.error.MemoryError;
import com.techshroom.slitheringlatte.python.error.NotImplementedError;
import com.techshroom.slitheringlatte.python.error.OverflowError;
import com.techshroom.slitheringlatte.python.underscore.LenSupported;
import com.techshroom.slitheringlatte.python.underscore.MulSupported;
import com.techshroom.slitheringlatte.python.underscore.ReprSupported;
import com.techshroom.slitheringlatte.python.underscore.StrSupported;

/**
 * Represents a Python-like String, which includes both {@code "b''"} (byte
 * strings) and {@code "(r|u)?''"} (regular strings).
 * 
 * @author Kenzie Togami
 */
public interface PythonLikeString extends ReprSupported, StrSupported,
        LenSupported, MulSupported, CharSequence {
    @Override
    default int length() {
        return len();
    }

    @Override
    default PythonLikeString repr() {
        return PythonString.wrapString(CommonStringUtils
                .pythonEscapedString(toString(), getPrefix()));
    }

    /**
     * Get the prefix on the string.
     * 
     * @return the prefix, like b for a bytes object.
     */
    default Optional<String> getPrefix() {
        return Optional.empty();
    }

    @Override
    default public PythonLikeString mul(Object other) {
        if (other instanceof Integer || other instanceof Long
                || other instanceof BigInteger) {
            long count = ((Number) other).longValue();
            if (other instanceof BigInteger || count > Integer.MAX_VALUE) {
                throw new OverflowError(
                        "cannot fit 'int' into an index-sized integer");
            }
            try {
                if (Math.multiplyExact(count, len()) > Integer.MAX_VALUE) {

                }
            } catch (ArithmeticException overflow) {
                throw new MemoryError();
            }
            // TODO implment
            return PythonString.wrapString(toString());
        }
        throw new NotImplementedError("Multiplication unsupported for object "
                + other + " of " + other.getClass());
    }
}
