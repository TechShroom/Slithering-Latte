package com.techshroom.slitheringlatte.helper.parsing;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

public final class Parsing {

    private static interface JoinInProgress {
        String by(Object by);
    }

    private static JoinInProgress join(Object pattern) {
        checkNotNull(pattern);
        String patStr = pattern.toString();
        return by -> "(?!" + patStr + checkNotNull(by).toString() + ")*"
                + patStr;
    }

    public static final Pattern VALID_JAVA_IDENTIFIER =
            Pattern.compile("\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*");
    public static final Pattern VALID_JAVA_PACKAGE = Pattern.compile("(?!"
            + VALID_JAVA_IDENTIFIER + "\\.)*");
    public static final Pattern VALID_JAVA_CLASS = Pattern
            .compile(VALID_JAVA_PACKAGE.pattern()
                    + VALID_JAVA_IDENTIFIER.pattern());
    private static final String SPACE = "\\s+";
    private static final String OPTSPACE = "\\s*";
    private static final Pattern GENERIC_SPLITTER = Pattern.compile(","
            + OPTSPACE);
    private static final Pattern GENERIC_BITS = Pattern.compile(OPTSPACE + "&"
            + OPTSPACE);
    private static final Pattern GENERIC_PATTERN = Pattern.compile("("
            + VALID_JAVA_IDENTIFIER + ")" + SPACE + "(extends|super)?" + SPACE
            + "(" + join(VALID_JAVA_CLASS).by(GENERIC_BITS) + ")");
    private static final Pattern MULTI_GENERIC_PATTERN = Pattern.compile(join(
            GENERIC_PATTERN).by(GENERIC_SPLITTER));
    private static final Pattern CLASS_PATTERN = Pattern.compile("("
            + VALID_JAVA_CLASS + ")" + OPTSPACE + "(?!<" + OPTSPACE + "("
            + MULTI_GENERIC_PATTERN + ")" + OPTSPACE + ">)?");

    public static Stream<Generic> parseGeneric(String raw) {
        checkArgument(MULTI_GENERIC_PATTERN.matcher(raw).matches(),
                "%s is an invalid generic string", raw);
        return GENERIC_SPLITTER
                .splitAsStream(raw)
                .map(str -> {
                    Matcher m = GENERIC_PATTERN.matcher(str);
                    checkState(m.matches(),
                            "%s is not a valid generic (impossible error)", str);
                    String name = m.group(1);
                    String relationStr = m.group(2);
                    String relatedBits = m.group(3);
                    if (relationStr != null) {
                        Iterable<String> relBitsIterator =
                                GENERIC_BITS.splitAsStream(relatedBits)::iterator;
                        return Generic.create(name,
                                Relation.valueOf(relationStr), relBitsIterator);
                    } else {
                        return Generic.create(name);
                    }
                });
    }

    public static ClassDefinition parseClassDefinition(String raw) {
        Matcher m = CLASS_PATTERN.matcher(raw);
        checkArgument(m.matches(), "%s is not a valid class definition", raw);
        String name = m.group(1);
        String genericStr = m.group(2);
        return ClassDefinition.create(
                name,
                Optional.ofNullable(genericStr).map(Parsing::parseGeneric)
                        .map(s -> (Iterable<Generic>) s::iterator)
                        .orElseGet(ImmutableList::of));
    }

    private Parsing() {
    }
}
