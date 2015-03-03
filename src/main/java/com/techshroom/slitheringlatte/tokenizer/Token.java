package com.techshroom.slitheringlatte.tokenizer;

import com.google.common.collect.ImmutableMap;

/**
 * Token types. Extracted from token.py and tokenizer.py
 * 
 * @author Kenzie Togami
 */
public enum Token {
    /**
     * End of stream.
     */
    ENDMARKER,
    /**
     * Name of binding, e.g. classes, functions, and variables.
     */
    NAME,
    /**
     * Number.
     */
    NUMBER,
    /**
     * String.
     */
    STRING,
    /**
     * Newline (\r and/or \n) outside of parenthesis.
     */
    NEWLINE,
    /**
     * Indentation increase.
     */
    INDENT,
    /**
     * Indentation decrease.
     */
    DEDENT,
    /**
     * Left parenthesis: '('
     */
    LPAR,
    /**
     * Right parenthesis: ')'
     */
    RPAR,
    /**
     * Left square bracket: '['
     */
    LSQB,
    /**
     * Right square bracket: ']'
     */
    RSQB,
    /**
     * Colon: ':'
     */
    COLON,
    /**
     * Comma: ','
     */
    COMMA,
    /**
     * Semicolon:';'
     */
    SEMI,
    /**
     * Plus sign:'+'
     */
    PLUS,
    /**
     * Minus sign: '-'
     */
    MINUS,
    /**
     * Star or asterisk: '*'
     */
    STAR,
    /**
     * Forward slash: '/'
     */
    SLASH,
    /**
     * Vertical bar or pipe: '|'
     */
    VBAR,
    /**
     * Ampersand: '&'
     */
    AMPER,
/**
 * Less than: '<'
 */
    LESS,
    /**
     * Greater than: '>'
     */
    GREATER,
    /**
     * Equals: '='
     */
    EQUAL,
    /**
     * Dot or period: '.'
     */
    DOT,
    /**
     * Percent: '%'
     */
    PERCENT,
    /**
     * Left brace or squiggly bracket: '{'
     */
    LBRACE,
    /**
     * Right brace or squiggly bracket: '}'
     */
    RBRACE,
    /**
     * Two equals: '=='
     */
    EQEQUAL,
    /**
     * Not equals: '!='
     */
    NOTEQUAL,
    /**
     * Less than or equals: '<='
     */
    LESSEQUAL,
    /**
     * Greater than or equals: '>='
     */
    GREATEREQUAL,
    /**
     * Tilde: '~'
     */
    TILDE,
    /**
     * Circumflex or caret: '^'
     */
    CIRCUMFLEX,
/**
 * Left shift: '<<'
 */
    LEFTSHIFT,
    /**
     * Right shift: '>>'
     */
    RIGHTSHIFT,
    /**
     * Two stars or asterisks: '**'
     */
    DOUBLESTAR,
    /**
     * Plus equals: '+='
     */
    PLUSEQUAL,
    /**
     * Minus equals: '-='
     */
    MINEQUAL,
    /**
     * Star equals or times equals: '*='
     */
    STAREQUAL,
    /**
     * Slash equals or divide equals: '/='
     */
    SLASHEQUAL,
    /**
     * Percent equals or modulo equals: '%='
     */
    PERCENTEQUAL,
    /**
     * Ampersand equals or and equals: '&='
     */
    AMPEREQUAL,
    /**
     * Vertical bar or pipe equals or or equals: '|='
     */
    VBAREQUAL,
    /**
     * Circumflex equals or xor equals: '^='
     */
    CIRCUMFLEXEQUAL,
    /**
     * Left shift equals: '<<='
     */
    LEFTSHIFTEQUAL,
    /**
     * Right shift equals: '>>='
     */
    RIGHTSHIFTEQUAL,
    /**
     * Double star or asterisk equals or power equals: '**='
     */
    DOUBLESTAREQUAL,
    /**
     * Double slash or int division: '//'
     */
    DOUBLESLASH,
    /**
     * Double slash equals or int divide equals: '//='
     */
    DOUBLESLASHEQUAL,
    /**
     * At: '@'
     */
    AT,
    /**
     * At equals or matrix multiply equals: '@='
     */
    ATEQUAL,
    /**
     * Right arrow: '->'
     */
    RARROW,
    /**
     * Ellipsis: '...'
     */
    ELLIPSIS,
    /**
     * Operator.
     */
    OP,
    /**
     * Error.
     */
    ERRORTOKEN,
    /**
     * Comment.
     */
    COMMENT,
    /**
     * Newline (\r and/or \n) inside of parenthesis.
     */
    NL,
    /**
     * Encoding.
     */
    ENCODING;
    /**
     * Number of tokens.
     */
    public static final int N_TOKENS = values().length;
    /**
     * A mapping from strings to token types. Used for the {@link #OP} token.
     */
    public static final ImmutableMap<String, Token> EXACT_TOKEN_TYPES;
    static {
        ImmutableMap.Builder<String, Token> builder = ImmutableMap.builder();
        builder.put("(", LPAR);
        builder.put(")", RPAR);
        builder.put("[", LSQB);
        builder.put("]", RSQB);
        builder.put(":", COLON);
        builder.put(",", COMMA);
        builder.put(";", SEMI);
        builder.put("+", PLUS);
        builder.put("-", MINUS);
        builder.put("*", STAR);
        builder.put("/", SLASH);
        builder.put("|", VBAR);
        builder.put("&", AMPER);
        builder.put("<", LESS);
        builder.put(">", GREATER);
        builder.put("=", EQUAL);
        builder.put(".", DOT);
        builder.put("%", PERCENT);
        builder.put("{", LBRACE);
        builder.put("}", RBRACE);
        builder.put("==", EQEQUAL);
        builder.put("!=", NOTEQUAL);
        builder.put("<=", LESSEQUAL);
        builder.put(">=", GREATEREQUAL);
        builder.put("~", TILDE);
        builder.put("^", CIRCUMFLEX);
        builder.put("<<", LEFTSHIFT);
        builder.put(">>", RIGHTSHIFT);
        builder.put("**", DOUBLESTAR);
        builder.put("+=", PLUSEQUAL);
        builder.put("-=", MINEQUAL);
        builder.put("*=", STAREQUAL);
        builder.put("/=", SLASHEQUAL);
        builder.put("%=", PERCENTEQUAL);
        builder.put("&=", AMPEREQUAL);
        builder.put("|=", VBAREQUAL);
        builder.put("^=", CIRCUMFLEXEQUAL);
        builder.put("<<=", LEFTSHIFTEQUAL);
        builder.put(">>=", RIGHTSHIFTEQUAL);
        builder.put("**=", DOUBLESTAREQUAL);
        builder.put("//", DOUBLESLASH);
        builder.put("//=", DOUBLESLASHEQUAL);
        builder.put("@", AT);
        builder.put("@=", ATEQUAL);
        EXACT_TOKEN_TYPES = builder.build();
    }

    /**
     * @return {@code ordinal() (name())} for looking like the python
     *         implementation.
     */
    @Override
    public String toString() {
        return ordinal() + " (" + name() + ")";
    }
}
