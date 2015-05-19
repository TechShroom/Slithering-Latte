package com.techshroom.slitheringlatte.tokenizer;

import com.google.auto.value.AutoValue;

/**
 * Token info class from tokenizer.py.
 * 
 * @author Kenzie Togami
 */
@AutoValue
public abstract class TokenInfo {
    /**
     * Create a new TokenInfo instance.
     * 
     * @param type
     *            - token type
     * @param string
     *            - string binding
     * @param start
     *            - start position
     * @param end
     *            - end position
     * @param line
     *            - last line
     * @return the created instance
     */
    public static final TokenInfo createInfo(Token type, String string,
            LinePosition start, LinePosition end, int line) {
        return new AutoValue_TokenInfo(type, string, start, end, line);
    }

    TokenInfo() {
    }

    /**
     * Token representing the type of this info.
     * 
     * @return a Token that represents the type of info contained in
     *         {@link #string()}.
     */
    public abstract Token type();

    /**
     * String bound to the type.
     * 
     * @return the string object attached to the info, it is of type
     *         {@link #type()}
     */
    public abstract String string();

    /**
     * Index of the start of {@link #string()} in the line.
     * 
     * @return index of the start of {@link #string()} in the line.
     */
    public abstract LinePosition start();

    /**
     * Index of the end of {@link #string()} in the line.
     * 
     * @return index of the end of {@link #string()} in the line
     */
    public abstract LinePosition end();

    /**
     * Line number of the string. If multi-line, the last line is this number.
     * 
     * @return the line number
     */
    public abstract int line();

    /**
     * Convert to an exact type. Used only for operator conversion to a more
     * defined type.
     * 
     * @return {@code type()} if {@code type()} is not {@link Token#OP},
     *         {@link Token#EXACT_TOKEN_TYPES}
     *         {@code .getOrDefault(string(), type())} otherwise
     */
    public final Token exact_type() {
        if (type() == Token.OP) {
            return Token.EXACT_TOKEN_TYPES.getOrDefault(string(), type());
        }
        return type();
    }

    @Override
    public String toString() {
        return String.format(
                "TokenInfo(type=%s, string=\"%s\", start=%s, end=%s, line=%s)",
                type(), string(), start(), end(), line());
    }
}
