package com.techshroom.slitheringlatte.python.string;

import okio.ByteString;

/**
 * Java equivalent of bytes().
 * 
 * @author Kenzie Togami
 */
public final class PythonBytes implements PythonLikeString {
    /**
     * Creates a new PythonBytes from the given characters.
     * 
     * @param contents
     *            - characters to wrap
     * @return a new PythonBytes with a UTF-8 encoded ByteString of the contents
     *         as the source
     */
    public static PythonBytes wrapChars(char[] contents) {
        return wrapString(String.copyValueOf(contents));
    }

    /**
     * Creates a new PythonBytes from the given string.
     * 
     * @param contents
     *            - string to wrap
     * @return a new PythonBytes with a UTF-8 encoded ByteString of the contents
     *         as the source
     */
    public static PythonBytes wrapString(String contents) {
        return ofByteString(ByteString.encodeUtf8(contents));
    }

    /**
     * Creates a new PythonBytes from the given bytes.
     * 
     * @param contents
     *            - bytes to wrap
     * @return a new PythonBytes with a ByteString of the contents as the source
     */
    public static PythonBytes wrapBytes(byte[] contents) {
        return ofByteString(ByteString.of(contents));
    }

    /**
     * Creates a new PythonBytes from the given ByteString.
     * 
     * @param bytes
     *            - The ByteString to wrap
     * @return a new PythonBytes with the given ByteString as the source
     */
    public static PythonBytes ofByteString(ByteString bytes) {
        return new PythonBytes(bytes);
    }

    private final ByteString bytes;

    private PythonBytes(ByteString bytes) {
        this.bytes = bytes;
    }

    /**
     * Get the raw bytes.
     * 
     * @return the raw bytes represented by this PythonBytes
     */
    public ByteString getBytes() {
        return bytes;
    }

    @Override
    public int len() {
        return bytes.size();
    }

    @Override
    public char charAt(int index) {
        return (char) (bytes.getByte(index) & 0xFF);
    }

    @Override
    public PythonBytes subSequence(int start, int end) {
        return PythonBytes.ofByteString(bytes.substring(start, end));
    }

    @Override
    public String toString() {
        return bytes.utf8();
    }
}
