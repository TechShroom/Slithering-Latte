package com.techshroom.slitheringlatte.python.string;

import static com.google.common.base.Preconditions.checkNotNull;

import com.techshroom.slitheringlatte.python.string.flags.ConstructionDetail;
import com.techshroom.slitheringlatte.python.string.flags.Quote;
import com.techshroom.slitheringlatte.python.string.flags.QuoteCount;

/**
 * Accurate representations of Python strings.
 * 
 * @author Kenzie Togami
 */
public final class PythonString implements PythonLikeString {
    /**
     * Wraps a Java string, which is always {@link Quote#DOUBLE},
     * {@link QuoteCount#SINGLE} and {@link ConstructionDetail#BARE}.
     * 
     * @param s
     *            - the original Java string.
     * @return a new PythonString wrapping the given Java string.
     */
    public static PythonString wrapString(String s) {
        return create(s, Quote.DOUBLE, QuoteCount.SINGLE,
                      ConstructionDetail.BARE);
    }

    /**
     * Creates a new PythonString, using the given details to accurately return
     * {@link #repr()}.
     * 
     * @param contents
     *            - contents, this does not get modified by any of the passed-in
     *            flags
     * @param quoteType
     *            - the type of quote, either {@code "} or {@code '}.
     * @param quoteCount
     *            - the amount of quotes used, either {@code "} or {@code """}.
     * @param originalFormMarker
     *            - the marker for the interpretation flag; {@code r}, {@code u}
     *            , or {@code None/null/Bare}.
     * @return the new PythonString
     */
    public static PythonString create(String contents, Quote quoteType,
            QuoteCount quoteCount, ConstructionDetail originalFormMarker) {
        return new PythonString(contents, quoteType, quoteCount,
                originalFormMarker);
    }

    private final String rawContents;
    private final Quote quotationType;
    private final QuoteCount quoteCount;
    private final ConstructionDetail originalFormMarker;

    private PythonString(String source, Quote qType, QuoteCount qCount,
            ConstructionDetail prefixMarker) {
        rawContents = checkNotNull(source);
        quotationType = checkNotNull(qType);
        quoteCount = checkNotNull(qCount);
        originalFormMarker = checkNotNull(prefixMarker);
    }

    /**
     * Gets this PythonString as a string. Also available as {@link #toString()}
     * .
     * 
     * @return the raw String contents of this string
     */
    public String getRawContents() {
        return rawContents;
    }

    /**
     * Gets the quotation type.
     * 
     * @return either {@link Quote#SINGLE} for {@code '} or {@link Quote#DOUBLE}
     *         for {@code "}.
     */
    public Quote getQuotationType() {
        return quotationType;
    }

    /**
     * Gets the quote count.
     * 
     * @return either {@link QuoteCount#SINGLE} for {@code ('|")} or
     *         {@link QuoteCount#TRIPLE} for {@code ('''|""")}
     */
    public QuoteCount getQuoteCount() {
        return quoteCount;
    }

    /**
     * Gets the original form marker.
     * 
     * @return either {@link ConstructionDetail#RAW} for {@code r''} or
     *         {@link ConstructionDetail#UNICODE} for {@code u''} or
     *         {@link ConstructionDetail#BARE} for {@code ''}
     */
    public ConstructionDetail getOriginalFormMarker() {
        return originalFormMarker;
    }

    @Override
    public int len() {
        return length();
    }

    @Override
    public int length() {
        return rawContents.length();
    }

    @Override
    public char charAt(int index) {
        return rawContents.charAt(index);
    }

    @Override
    public PythonString subSequence(int start, int end) {
        return create(rawContents.substring(start, end), quotationType,
                      quoteCount, originalFormMarker);
    }

    @Override
    public String toString() {
        return rawContents;
    }
}
