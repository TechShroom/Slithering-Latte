package com.techshroom.slitheringlatte.tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableMap;
import com.techshroom.slitheringlatte.array.EmptyArray;

/**
 * Python tokenizer, rewritten from tokenizer.py.
 * 
 * @author Kenzie Togami
 */
public abstract class Tokenizer {
    private static final String COOKIE_RE_SRC =
            "^[ \\t\\f]*#.*coding[:=][ \\t]*([-\\w.]+)";
    private static final String BLANK_RE_SRC = "^[ \\t\\f]*(?:[#\\r\\n]|$)";
    private static final Pattern COOKIE_RE = Pattern.compile(COOKIE_RE_SRC);
    private static final Pattern BLANK_RE = Pattern.compile(BLANK_RE_SRC);

    private static final class PatternBuilder {
        public static Pattern compile(String s) {
            return Pattern.compile(s, Pattern.UNICODE_CASE | Pattern.CANON_EQ
                    | Pattern.UNICODE_CHARACTER_CLASS);
        }

        private static String[] convToString(Object... objs) {
            return partialConvToString(objs).toArray(
                    EmptyArray.STRING.getRegular().get());
        }

        private static List<String> partialConvToString(Object... objs) {
            List<String> res = new ArrayList<>(objs.length);
            for (Object object : objs) {
                res.add(object.toString());
            }
            return res;
        }

        public static PatternBuilder create() {
            return new PatternBuilder();
        }

        public PatternBuilder() {
        }

        public Pattern concat(Object... objs) {
            List<String> strings = partialConvToString(objs);
            String pat = String.join("", strings);
            return compile(pat);
        }

        public Pattern group(Object... objs) {
            return compile(_group(convToString(objs)));
        }

        private String _group(String... strings) {
            return "(" + String.join("|", strings) + ")";
        }

        public Pattern any(Object... objs) {
            return compile(_any(convToString(objs)));
        }

        private String _any(String... strings) {
            return _group(strings) + "*";
        }

        public Pattern maybe(Object... objs) {
            return compile(_maybe(convToString(objs)));
        }

        private String _maybe(String... strings) {
            return _group(strings) + "?";
        }

        public Pattern _compile(String s) {
            return compile(s);
        }
    }

    /*
     * Note: we use unicode matching for names ("\w") but ASCII matching for
     * number literals.
     */
    private static final PatternBuilder builder = PatternBuilder.create();
    private static final Pattern Whitespace = builder._compile("[ \\f\\t]*");
    private static final Pattern Comment = builder._compile("#[^\\r\\n]*");
    private static final Pattern Ignore = builder.concat(Whitespace,
            builder.any("\\\\\\r?\\n" + Whitespace.pattern()),
            builder.maybe(Comment));
    private static final Pattern Name = builder._compile("\\w+");
    private static final Pattern Hexnumber = builder
            ._compile("0[xX][0-9a-fA-F]+");
    private static final Pattern Binnumber = builder._compile("0[bB][01]+");
    private static final Pattern Octnumber = builder._compile("0[oO][0-7]+");
    private static final Pattern Decnumber = builder
            ._compile("(?:0+|[1-9][0-9]*)");
    private static final Pattern Intnumber = builder.group(Hexnumber,
            Binnumber, Octnumber, Decnumber);
    private static final Pattern Exponent = builder._compile("[eE][-+]?[0-9]+");
    private static final Pattern Pointfloat = builder.concat(
            builder.group("[0-9]+\\.[0-9]*", "\\.[0-9]+"),
            builder.maybe(Exponent));
    private static final Pattern Expfloat = builder.concat("[0-9]+", Exponent);
    private static final Pattern Floatnumber = builder.group(Pointfloat,
            Expfloat);
    private static final Pattern Imagnumber = builder.group("[0-9]+[jJ]",
            builder.concat(Floatnumber, "[jJ]"));
    private static final Pattern Number = builder.group(Imagnumber,
            Floatnumber, Intnumber);
    private static final String StringPrefix = "(?:[bB][rR]?|[rR][bB]?|[uU])?";
    /**
     * Tail end of single quote (') string.
     */
    private static final Pattern Single = builder.concat(StringPrefix,
            "[^'\\\\]*(?:\\\\.[^'\\\\]*)*'");
    /**
     * Tail end of double quote (") string.
     */
    private static final Pattern Double = builder.concat(StringPrefix,
            "[^\"\\\\]*(?:\\\\.[^\"\\\\]*)*\"");
    /**
     * Tail end of triple single quote (''') string.
     */
    private static final Pattern Single3 = builder.concat(StringPrefix,
            "[^'\\\\]*(?:(?:\\\\.|'(?!''))[^'\\\\]*)*'''");
    /**
     * Tail end of triple double quote (""") string.
     */
    private static final Pattern Double3 = builder.concat(StringPrefix,
            "[^\"\\\\]*(?:(?:\\\\.|\"(?!\"\"))[^\"\\\\]*)*\"\"\"");
    private static final Pattern Triple = builder.group(StringPrefix + "'''",
            StringPrefix + "\"\"\"");
    /**
     * Single-line ' or " string.
     */
    private static final Pattern String_ = builder.group(StringPrefix
            + "'[^\\n'\\\\]*(?:\\\\.[^\\n'\\\\]*)*'", StringPrefix
            + "\"[^\\n\"\\\\]*(?:\\\\.[^\\n\"\\\\]*)*\"");
    private static final Pattern Operator = builder.group("\\*\\*=?", ">>=?",
            "<<=?", "!=", "//=?", "->", "[+\\-*/%&@|^=<>]=?", "~");
    private static final String Bracket = "[\\[\\](){}]";
    private static final Pattern Special = builder.group("\\r?\\n",
            "\\.\\.\\.", "[:;.,@]");
    private static final Pattern Funny = builder.group(Operator, Bracket,
            Special);
    private static final Pattern PlainToken = builder.group(Number, Funny,
            String_, Name);
    private static final Pattern Token = builder.concat(Ignore, PlainToken);
    /**
     * First (or only) line of ' or " string.
     */
    private static final Pattern ContStr = builder.group(builder.concat(
            StringPrefix,
            "'[^\\n'\\\\]*(?:\\\\.[^\\n'\\\\]*)*"
                    + builder.group("'", "\\\\\\r?\\n")), builder.concat(
            StringPrefix, "\"[^\\n\"\\\\]*(?:\\\\.[^\\n\"\\\\]*)*",
            builder.group('"', "\\\\\\r?\\n")));
    private static final Pattern PseudoExtras = builder.group(
            "\\\\\\r?\\n|\\Z", Comment, Triple);
    private static final Pattern PseudoToken = builder.concat(Whitespace,
            builder.group(PseudoExtras, Number, Funny, ContStr, Name));
    private static final ImmutableMap<String, Pattern> endpats;
    static {
        ImmutableMap.Builder<String, Pattern> builder = ImmutableMap.builder();
        builder.put("'", Single);
        builder.put("\"", Double);
        builder.put("'''", Single3);
        builder.put("\"\"\"", Double3);
        builder.put("r'''", Single3);
        builder.put("r\"\"\"", Double3);
        builder.put("b'''", Single3);
        builder.put("b\"\"\"", Double3);
        builder.put("R'''", Single3);
        builder.put("R\"\"\"", Double3);
        builder.put("B'''", Single3);
        builder.put("B\"\"\"", Double3);
        builder.put("br'''", Single3);
        builder.put("br\"\"\"", Double3);
        builder.put("bR'''", Single3);
        builder.put("bR\"\"\"", Double3);
        builder.put("Br'''", Single3);
        builder.put("Br\"\"\"", Double3);
        builder.put("BR'''", Single3);
        builder.put("BR\"\"\"", Double3);
        builder.put("rb'''", Single3);
        builder.put("rb\"\"\"", Double3);
        builder.put("Rb'''", Single3);
        builder.put("Rb\"\"\"", Double3);
        builder.put("rB'''", Single3);
        builder.put("rB\"\"\"", Double3);
        builder.put("RB'''", Single3);
        builder.put("RB\"\"\"", Double3);
        builder.put("u'''", Single3);
        builder.put("u\"\"\"", Double3);
        builder.put("R'''", Single3);
        builder.put("R\"\"\"", Double3);
        builder.put("U'''", Single3);
        builder.put("U\"\"\"", Double3);
        builder.put("r", null);
        builder.put("R", null);
        builder.put("b", null);
        builder.put("B", null);
        builder.put("u", null);
        builder.put("U", null);
        endpats = builder.build();
    }
    private static final ImmutableMap<String, Pattern> triple_quoted;
    static {
        ImmutableMap.Builder<String, Pattern> builder = ImmutableMap.builder();
        String[] put =
                { "'''", "\"\"\"", "r'''", "r\"\"\"", "R'''", "R\"\"\"",
                        "b'''", "b\"\"\"", "B'''", "B\"\"\"", "br'''",
                        "br\"\"\"", "Br'''", "Br\"\"\"", "bR'''", "bR\"\"\"",
                        "BR'''", "BR\"\"\"", "rb'''", "rb\"\"\"", "rB'''",
                        "rB\"\"\"", "Rb'''", "Rb\"\"\"", "RB'''", "RB\"\"\"",
                        "u'''", "u\"\"\"", "U'''", "U\"\"\"" };
        for (String string : put) {
            builder.put(string, Tokenizer.builder._compile(string));
        }
        triple_quoted = builder.build();
    }
    private static final ImmutableMap<String, Pattern> single_quoted;
    static {
        ImmutableMap.Builder<String, Pattern> builder = ImmutableMap.builder();
        String[] put =
                { "'", "\"", "r'", "r\"", "R'", "R\"", "b'", "b\"", "B'",
                        "B\"", "br'", "br\"", "Br'", "Br\"", "bR'", "bR\"",
                        "BR'", "BR\"", "rb'", "rb\"", "rB'", "rB\"", "Rb'",
                        "Rb\"", "RB'", "RB\"", "u'", "u\"", "U'", "U\"" };
        for (String string : put) {
            builder.put(string, Tokenizer.builder._compile(string));
        }
        single_quoted = builder.build();
    }
}
