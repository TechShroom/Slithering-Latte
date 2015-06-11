package com.techshroom.slitheringlatte.tokenizer;

import java.util.List;

import com.google.common.collect.Lists;

public final class Untokenizer {

    private List<String> tokens = Lists.newArrayList();
    private int prev_row = 1;
    private int prev_col = 0;
    private String encoding = null;

    /**
     * @param start
     */
    public void add_whitespace(LinePosition start) {
        int row = start.getLine(), col = start.getPosition();
        if (row < this.prev_row
                || (row == this.prev_row && col < this.prev_col)) {
            throw new IllegalStateException(String.format(
                    "start ({}, {}) precedes previous end ({}, {})", row, col,
                    this.prev_row, this.prev_col));
        }
        int row_offset = row - this.prev_row;
        if (row_offset != 0) {
            this.tokens.add("\\\n");
            this.prev_col = 0;
        }
    }
}
