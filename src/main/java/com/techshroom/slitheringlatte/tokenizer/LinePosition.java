package com.techshroom.slitheringlatte.tokenizer;

import com.google.auto.value.AutoValue;

/**
 * Line position for start and end of TokenInfo. Tuple equivalent is (line,
 * position).
 * 
 * @author Kenzie Togami
 */
@AutoValue
public abstract class LinePosition {
    /**
     * Creates a new line position.
     * 
     * @param line
     *            - line number
     * @param pos
     *            - position in the line
     * @return a new LinePosition containing the given values
     */
    public static final LinePosition create(int line, int pos) {
        return new AutoValue_LinePosition(line, pos);
    }

    LinePosition() {
    }

    /**
     * Get the line number.
     * 
     * @return the line number of this LinePosition
     */
    public abstract int getLine();

    /**
     * Get the position.
     * 
     * @return the position in the line of the LinePosition
     */
    public abstract int getPosition();

    @Override
    public String toString() {
        return new StringBuilder("(").append(getLine()).append(", ")
                .append(getPosition()).append(')').toString();
    }
}