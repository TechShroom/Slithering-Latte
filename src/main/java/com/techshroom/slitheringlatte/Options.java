package com.techshroom.slitheringlatte;

import java.io.IOException;
import java.util.Arrays;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

/**
 * Options keeper.
 * 
 * @author Kenzie Togami
 */
public enum Options {
    /**
     * Options keeper instance.
     */
    INSTANCE;

    /**
     * Means either STDIN or STDOUT, depending on context.
     */
    public static final String STREAM = "-";

    /**
     * {@code true} if debug output should be on
     */
    public static boolean DEBUG = false;

    private ArgumentAcceptingOptionSpec<String> input, output;
    private OptionSpec<Void> debug;

    private final OptionParser parser = new OptionParser();
    {
        doParserSetup();
    }

    private void doParserSetup() {
        input =
                parser.acceptsAll(Arrays.asList("i", "input"),
                                  "Input file (.py usually)").withRequiredArg()
                        .defaultsTo(STREAM);
        output =
                parser.acceptsAll(Arrays.asList("o", "output"),
                                  "Output file (.class usually)")
                        .withRequiredArg().defaultsTo(STREAM);
        debug = parser.accepts("debug", "Turns on debug output");
    }

    /**
     * Returns the input option spec.
     * 
     * @return the input option spec.
     */
    public ArgumentAcceptingOptionSpec<String> inputOpt() {
        return input;
    }

    /**
     * Returns the input output spec.
     * 
     * @return the input output spec.
     */
    public ArgumentAcceptingOptionSpec<String> outputOpt() {
        return output;
    }

    /**
     * Returns the input output spec.
     * 
     * @return the input output spec.
     */
    public OptionSpec<Void> debugOpt() {
        return debug;
    }

    /**
     * Get the parser currently in use.
     * 
     * @return the active parser
     */
    public OptionParser getParser() {
        return parser;
    }

    /**
     * Parse the given arguments into an OptionSet.
     * 
     * @param args
     *            - arguments to parse
     * @return the parsed arguments
     */
    public OptionSet parse(String... args) {
        OptionSet opts = getParser().parse(args);
        DEBUG = opts.has(debug);
        return opts;
    }

    /**
     * Print help to standard out.
     * 
     * @throws IOException
     *             see {@link OptionParser#printHelpOn(java.io.OutputStream)}.
     */
    public void printHelp() throws IOException {
        getParser().printHelpOn(System.out);
    }
}
