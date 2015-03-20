package com.techshroom.slitheringlatte.python.string;

import static com.google.common.base.Preconditions.checkState;

import java.util.Optional;
import java.util.function.IntConsumer;

/**
 * Shared utilities between implementations of PythonLikeString.
 * 
 * @author Kenzie Togami
 */
public final class CommonStringUtils {
    private static final String[] HEX_TABLE = "0123456789abcdef".split("");
    private static final ThreadLocal<StringBuilder> builder = ThreadLocal
            .withInitial(StringBuilder::new);

    private static StringBuilder getStringBuilder() {
        return builder.get();
    }

    private static StringBuilder resetAndGetStringBuilder() {
        StringBuilder b = builder.get();
        return b.delete(0, b.length());
    }

    private static IntConsumer makeDoEscape(char quote) {
        StringBuilder out = resetAndGetStringBuilder();
        return ch -> {
            checkState(ch <= 0x7FFF,
                       "character out of range, we need to encode 21-bits (%s)",
                       ch);
            if (ch == '\\' || ch == quote) {
                // Escape backslashes
                out.append('\\');
                out.append((char) ch);
            } else if (ch >= 256) {
                // Map 16-bit characters to '\\uxxxx'
                out.append('\\');
                out.append('u');
                out.append(HEX_TABLE[(ch >> 12) & 0x000F]);
                out.append(HEX_TABLE[(ch >> 8) & 0x000F]);
                out.append(HEX_TABLE[(ch >> 4) & 0x000F]);
                out.append(HEX_TABLE[ch & 0x000F]);
            } else if (ch == '\t') {
                out.append('\\');
                out.append('t');
            } else if (ch == '\n') {
                out.append('\\');
                out.append('n');
            } else if (ch == '\r') {
                out.append('\\');
                out.append('r');
            } else if (ch < ' ' || ch >= 0x7F) {
                // Map non-printable US ASCII to '\xhh'
                out.append('\\');
                out.append('x');
                out.append(HEX_TABLE[(ch >> 4) & 0x000F]);
                out.append(HEX_TABLE[ch & 0x000F]);
            } else {
                out.append((char) ch);
            }
        };
    }

    /**
     * Common Python escape for PythonBytes and PythonString, with no prefix so
     * that there's no generic issues with {@link Optional#empty()}.
     * 
     * @param input
     *            - stored string
     * @return the representation of the input string
     */
    public static String pythonEscapedString(String input) {
        return pythonEscapedString(input, Optional.<String> empty());
    }

    /**
     * Common Python escape for PythonBytes and PythonString.
     * 
     * @param input
     *            - stored string
     * @param prefix
     *            - prefix if there is one
     * @return the representation of the input string
     */
    public static String pythonEscapedString(String input,
            Optional<String> prefix) {
        StringBuilder out = getStringBuilder();
        prefix.ifPresent(out::append);
        char quote = input.contains("\"") ? '\'' : '"';
        input.chars().forEachOrdered(makeDoEscape(quote));
        return out.toString();
    }

    private CommonStringUtils() {
        throw new AssertionError();
    }
}
