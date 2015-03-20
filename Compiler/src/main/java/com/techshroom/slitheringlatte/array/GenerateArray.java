package com.techshroom.slitheringlatte.array;

/**
 * Array generators.
 * 
 * @author Kenzie Togami
 */
public final class GenerateArray {
    private GenerateArray() {
        throw new AssertionError();
    }

    /**
     * Make an array of the lines in the given String. Newlines may be done with
     * CR, LF, or CRLF.
     * 
     * @param multiline
     *            - a possibly multiline String
     * @return an array containing the lines
     */
    public static String[] ofLinesInString(String multiline) {
        return multiline.replace("\r\n", "\n").replace('\r', '\n').split("\n");
    }
}
