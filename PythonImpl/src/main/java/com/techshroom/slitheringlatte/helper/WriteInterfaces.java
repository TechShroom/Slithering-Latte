package com.techshroom.slitheringlatte.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.lang.model.element.Modifier;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.ValueConverter;

import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Primitives;
import com.squareup.javapoet.*;
import com.techshroom.slitheringlatte.array.EmptyArray;
import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.InterfaceType.Value;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.DunderAttribute;
import com.techshroom.slitheringlatte.python.interfaces.Writable;

/**
 * Writes all annotations defined in the main resources
 * {@code config/annotations.txt}.
 * 
 * @author Kenzie Togami
 */
final class WriteInterfaces {

    private static final Path BASE = Paths.get("src", "main");
    private static final Path RESOURCES = BASE.resolve("resources");
    private static final Path CODE = BASE.resolve("java");
    private static final Path INTERFACE_SRC = RESOURCES.resolve(Paths
            .get("config", "interfaces.txt"));
    private static final Splitter SPACE = Splitter.on(' ');
    private static final Splitter PIPE = Splitter.on('|');

    private static void modExceptionTraceWithLine(Exception e, int line) {
        StackTraceElement[] original = e.getStackTrace();
        StackTraceElement[] expanded =
                Arrays.copyOf(original, original.length + 1);
        expanded[original.length] =
                new StackTraceElement("config/interfaces", "txt", INTERFACE_SRC
                        .toString().replace(File.separatorChar, '/'), line);
        e.setStackTrace(expanded);
    }

    public static void main(String[] args) {
        if (!Files.exists(INTERFACE_SRC)) {
            return;
        }
        try (Stream<String> lines = Files.lines(INTERFACE_SRC)) {
            List<String> unfiltered = lines.collect(Collectors.toList());
            List<String> uncollapsed =
                    unfiltered.stream().map(x -> {
                        int index = x.indexOf('#');
                        if (index > -1) {
                            return x.substring(0, index);
                        } else {
                            return x;
                        }
                    }).map(String::trim)
                            .filter(x -> !(x.startsWith("#") || x.isEmpty()))
                            .collect(Collectors.toList());
            for (int i = 0; i < uncollapsed.size(); i++) {
                String current = uncollapsed.get(i);
                // we don't preserve the # stuff
                int startLine = unfiltered.indexOf(current) + 1;
                while (!current.endsWith(";")) {
                    i++;
                    if (i >= uncollapsed.size()) {
                        IllegalStateException throwing =
                                new IllegalStateException(
                                        "Missing semi-colon starting at line "
                                                + startLine);
                        modExceptionTraceWithLine(throwing, startLine);
                        throw throwing;
                    }
                    current += uncollapsed.get(i);
                }
                writeInterface(current.substring(0, current.length() - 1),
                               startLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final TypeName WRITABLE_TYPE = ClassName.get(Writable.class);
    private static final TypeName UNDERSCORE_TYPE = ClassName
            .get(DunderAttribute.class);
    private static final String PACKAGE =
            "com.techshroom.slitheringlatte.python.interfaces.generated";

    // parser converters
    private static final ValueConverter<Class<?>> CLASS_CONVERTER =
            new ClassConverter();

    private static final Function<String, Class<?>> TO_CLASS =
            CLASS_CONVERTER::convert;

    private static final ValueConverter<TypeName> TYPENAME_CONVERTER =
            new TypeNameConverter();

    private static final Function<String, TypeName> TO_TYPENAME =
            TYPENAME_CONVERTER::convert;

    private static final class TypeNameConverter implements
            ValueConverter<TypeName> {
        private final Map<String, TypeName> typeNames = new HashMap<>();

        @Override
        public TypeName convert(String key) {
            return this.typeNames.computeIfAbsent(key, this::convertImpl);
        }

        private TypeName convertImpl(String value) {
            String className = value;
            String genericToParse = null;
            // 1. Extract generic value
            int genericIndex = value.indexOf('<');
            if (genericIndex >= 0) {
                className = value.substring(0, genericIndex);
                genericToParse =
                        value.substring(genericIndex + 1, value.length() - 1);
            }
            // 2. Return if only generic
            if (genericIndex == 0) {
                return TypeVariableName.get(genericToParse);
            }
            // 3. Translate to Class<?>
            Class<?> javaClassRepr = TO_CLASS.apply(className);
            // 4. Save via TypeName to match primitives
            TypeName result = TypeName.get(javaClassRepr);
            // 5. Apply generic if there
            if (genericToParse != null) {
                TypeName[] generic =
                        StreamSupport
                                .stream(PIPE.split(genericToParse)
                                                .spliterator(),
                                        false).map(this::convert)
                                .toArray(TypeName[]::new);
                result = ParameterizedTypeName.get((ClassName) result, generic);
            }
            // 6. Return result
            return result;
        }

        @Override
        public Class<? extends TypeName> valueType() {
            return TypeName.class;
        }

        @Override
        public String valuePattern() {
            return null;
        }

    }

    private static final class ClassConverter implements
            ValueConverter<Class<?>> {
        private final List<String> importedPackages = ImmutableList
                .of("java.lang",
                    "java.util",
                    "com.techshroom.slitheringlatte.python",
                    "com.techshroom.slitheringlatte.python.annotations",
                    "com.techshroom.slitheringlatte.python.interfaces",
                    PACKAGE);
        private final Multiset<String> trySet = HashMultiset.create();
        private final Map<String, Class<?>> classes = new HashMap<>();

        @Override
        public Class<?> convert(String key) {
            return this.classes.computeIfAbsent(key, this::convertImpl);
        }

        private Class<?> convertImpl(String value) {
            int tries = this.trySet.count(value);
            Supplier<RuntimeException> exec =
                    () -> new IllegalArgumentException(value
                            + " is not a class");
            Function<Exception, RuntimeException> execWithCause =
                    cause -> (RuntimeException) exec.get().initCause(cause);
            if (value.endsWith("[]")) {
                Class<?> converted =
                        convert(value.substring(0, value.length() - 2));
                return EmptyArray.of(converted).getUnsafe().getClass();
            }
            if (Primitives.allPrimitiveTypes().stream().map(Class::getName)
                    .anyMatch(s -> s.equals(value))) {
                // primitive type!
                return Primitives.allPrimitiveTypes().stream()
                        .filter(c -> c.getName().equals(value)).findFirst()
                        .orElseThrow(exec);
            }
            try {
                return Class.forName(value, false, Thread.currentThread()
                        .getContextClassLoader());
            } catch (ClassNotFoundException e) {
                // try again with java.lang
                if (value.indexOf('.') < 0) {
                    Optional<Class<?>> found =
                            this.importedPackages.stream()
                                    .map(pack -> pack + '.' + value)
                                    .<Class<?>> map(this::errorFreeConvert)
                                    .filter(Objects::nonNull).findFirst();
                    if (tries > 10) {
                        System.err.println("WARNING: " + value
                                + " took to long to resolve");
                        return found.orElseThrow(() -> execWithCause.apply(e));
                    } else {
                        this.trySet.add(value);
                        return found.orElseGet(() -> {
                            // patch to stop missing classes from killing it
                                try {
                                    System.err.println("Sleeping for 1s on "
                                            + value);
                                    Thread.sleep(1000);
                                } catch (InterruptedException ignored) {
                                }
                                return convert(value);
                            });
                    }
                } else {
                    throw execWithCause.apply(e);
                }
            } finally {
                if (tries == 0) {
                    // first one
                    this.trySet.setCount(value, 0);
                }
            }
        }

        private final Class<?> errorFreeConvert(String value) {
            try {
                return Class.forName(value, false, Thread.currentThread()
                        .getContextClassLoader());
            } catch (ClassNotFoundException ignored) {
                return null;
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public Class<? extends Class<?>> valueType() {
            // sigh
            return (Class<? extends Class<?>>) (Object) Class.class;
        }

        @Override
        public String valuePattern() {
            return "fully_qualifed_name";
        }
    };

    public static enum IType {

        ATTRIBUTE(Value.ATTRIBUTE, SUPERTYPES, PARAMETERS), METHOD(
                Value.METHOD, SUPERTYPES), MIX(Value.MIX, ATTR_METHOD_NAME,
                ATTR_TYPE);

        public final Value linkedValue;
        public final List<OptionSpec<?>> unusedOptions;
        public final AnnotationSpec spec;

        private IType(Value linked, OptionSpec<?>... options) {
            this.linkedValue = linked;
            this.unusedOptions = ImmutableList.copyOf(options);
            this.spec =
                    AnnotationSpec
                            .builder(InterfaceType.class)
                            .addMember("value",
                                       "$T.$L",
                                       Value.class,
                                       linked.name()).build();
        }
    }

    // some parsing info...
    private static final OptionParser PARSER = new OptionParser();
    private static final ArgumentAcceptingOptionSpec<IType> TYPE = PARSER
            .acceptsAll(ImmutableList.of("i", "interface-type"),
                        "The type of the interface (ATTRIBUTE, MIX, METHOD)")
            .withRequiredArg().ofType(IType.class).required();
    private static final ArgumentAcceptingOptionSpec<String> JAVA_NAME = PARSER
            .acceptsAll(ImmutableList.of("n", "name"),
                        "Name of the Java annotation.").withRequiredArg()
            .required();
    private static final ArgumentAcceptingOptionSpec<String> SUPERTYPES =
            PARSER.acceptsAll(ImmutableList.of("s", "supertype"),
                              "'Supertypes' of annotation, "
                                      + "actually just applied meta-annotations.")
                    .withRequiredArg().withValuesSeparatedBy(',')
                    .defaultsTo(new String[0]);
    private static final ArgumentAcceptingOptionSpec<String> PYTHON_NAME =
            PARSER.acceptsAll(ImmutableList.of("p", "python-name"),
                              "Name of the Java annotation.").withRequiredArg();
    private static final ArgumentAcceptingOptionSpec<Boolean> WRITABLE = PARSER
            .acceptsAll(ImmutableList.of("w", "writable"),
                        "Boolean value for writing.").withOptionalArg()
            .ofType(Boolean.class).defaultsTo(true);
    private static final ArgumentAcceptingOptionSpec<Boolean> OVERRIDES =
            PARSER.acceptsAll(ImmutableList.of("o", "overrides"),
                              "Boolean value for overriding.")
                    .withOptionalArg().ofType(Boolean.class).defaultsTo(true);
    private static final ArgumentAcceptingOptionSpec<String> ATTR_METHOD_NAME =
            PARSER.acceptsAll(ImmutableList.of("a", "attribute-func-name"),
                              "Name for the getter/setter function")
                    .requiredUnless(PYTHON_NAME, SUPERTYPES).withRequiredArg();
    private static final ArgumentAcceptingOptionSpec<TypeName> ATTR_TYPE =
            PARSER.acceptsAll(ImmutableList.of("t", "attribute-type"),
                              "The type of the attribute")
                    .requiredUnless(SUPERTYPES).withRequiredArg()
                    .withValuesConvertedBy(TYPENAME_CONVERTER);
    private static final ArgumentAcceptingOptionSpec<String> PARAMETERS =
            PARSER.acceptsAll(ImmutableList.of("m", "parameters"),
                              "The parameters for the method")
                    .withRequiredArg().withValuesSeparatedBy(',')
                    .defaultsTo(new String[] {});
    private static final ArgumentAcceptingOptionSpec<String> CODEBLOCK = PARSER
            .acceptsAll(ImmutableList.of("c", "codeblock"),
                        "The codeblock for the method").withRequiredArg();
    private static final Pattern PARAMETER_SPEC_REGEX = Pattern
            .compile("(.+):(.+?)(?:@(.+))?");

    private static <T> Optional<T> argOpt(OptionSpec<T> opt, OptionSet opts) {
        return Optional.ofNullable(opts.valueOf(opt));
    }

    private static Optional<String> regexGroupOpt(Matcher m, int group) {
        return m.groupCount() <= group ? Optional.ofNullable(m.group(group))
                                      : Optional.empty();
    }

    private static void writeInterface(String line, int lineNumber) {
        try {
            OptionSet lineOpts =
                    PARSER.parse(StreamSupport.stream(SPACE.split(line)
                                                              .spliterator(),
                                                      false)
                            .toArray(String[]::new));
            String classDetails = JAVA_NAME.value(lineOpts);
            int genericIndex = classDetails.indexOf('<');
            String name = classDetails;
            List<String> generics = ImmutableList.of();
            if (genericIndex > 0) {
                name = name.substring(0, genericIndex);
                generics =
                        PIPE.splitToList(classDetails
                                .substring(genericIndex + 1,
                                           classDetails.length() - 1));
            }
            IType type = TYPE.value(lineOpts);
            type.unusedOptions
                    .stream()
                    .filter(lineOpts::has)
                    .forEach(opt -> System.err.println("Ignoring " + opt
                            + " on line " + lineNumber));
            TypeSpec.Builder iface =
                    TypeSpec.interfaceBuilder(name)
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(type.spec);
            if (lineOpts.has(PYTHON_NAME)) {
                iface.addAnnotation(AnnotationSpec.builder(PythonName.class)
                        .addMember("value", "$S", PYTHON_NAME.value(lineOpts))
                        .build());
            }
            iface.addTypeVariables(generics.stream().map(TypeVariableName::get)
                    .collect(Collectors.toList()));
            if (type == IType.ATTRIBUTE || type == IType.METHOD) {
                String methodName =
                        argOpt(ATTR_METHOD_NAME, lineOpts)
                                .orElseGet(() -> argOpt(PYTHON_NAME, lineOpts)
                                        .map(s -> s.replace("__", ""))
                                        .orElseThrow(() -> new IllegalStateException(
                                                "no name")));
                Supplier<MethodSpec.Builder> methodBase =
                        () -> {
                            MethodSpec.Builder b =
                                    MethodSpec.methodBuilder(methodName);
                            if (lineOpts.has(OVERRIDES)
                                    && OVERRIDES.value(lineOpts)) {
                                b.addAnnotation(Override.class);
                            }
                            if (lineOpts.has(CODEBLOCK)) {
                                b.addModifiers(Modifier.PUBLIC,
                                               Modifier.DEFAULT)
                                        .addCode(CodeBlock
                                                .builder()
                                                .addStatement(CODEBLOCK
                                                        .value(lineOpts)
                                                        .replace("$", "$$")
                                                        .replace("%20", " "))
                                                .build());
                            } else {
                                b.addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT);
                            }
                            return b;
                        };
                TypeName returnType = ATTR_TYPE.value(lineOpts);
                boolean isWritable =
                        lineOpts.has(WRITABLE) ? WRITABLE.value(lineOpts)
                                              : false;
                if (isWritable) {
                    iface.addSuperinterface(WRITABLE_TYPE);
                    MethodSpec setter =
                            methodBase.get()
                                    .addParameter(returnType, methodName)
                                    .build();
                    iface.addMethod(setter);
                } else {
                    iface.addSuperinterface(UNDERSCORE_TYPE);
                }
                MethodSpec getter =
                        methodBase.get().returns(returnType).build();
                if (type == IType.METHOD) {
                    getter =
                            getter.toBuilder()
                                    .addParameters(PARAMETERS
                                            .values(lineOpts)
                                            .stream()
                                            .map(WriteInterfaces::generateParameterSpec)
                                            .collect(Collectors.toList()))
                                    .build();
                }
                iface.addMethod(getter);
            } else if (type == IType.MIX) {
                for (String superType : SUPERTYPES.values(lineOpts)) {
                    // process SUPERTYPE things
                    iface.addSuperinterface(generateSuperTypeName(superType));
                }
            }
            JavaFile out =
                    JavaFile.builder(PACKAGE, iface.build()).indent("    ")
                            .skipJavaLangImports(true).addFileComment("")
                            .build();
            try {
                out.writeTo(CODE);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed on " + line + ":" + lineNumber);
            }
        } catch (Exception e) {
            // failure to parse, try to insert line
            modExceptionTraceWithLine(e, lineNumber);
            throw e;
        }
    }

    private static ParameterSpec generateParameterSpec(String param) {
        Matcher data = PARAMETER_SPEC_REGEX.matcher(param);
        if (!data.matches()) {
            throw new IllegalArgumentException(param
                    + " is not a parameter string (<type>:<name>@<annotation>)");
        }
        Optional<String> annot = regexGroupOpt(data, 3);
        String type = data.group(1);
        TypeName typeName;
        if (type.startsWith("generic.")) {
            // generic type
            typeName = TO_TYPENAME.apply("<" + type.substring(8) + ">");
        } else {
            typeName = generateSuperTypeName(data.group(1));
        }
        ParameterSpec.Builder builder =
                ParameterSpec.builder(typeName, data.group(2));
        annot.map(TO_TYPENAME).map(ClassName.class::cast)
                .ifPresent(builder::addAnnotation);
        return builder.build();
    }

    private static TypeName generateSuperTypeName(String superType) {
        int idx = superType.indexOf('<');
        if (idx < 0) {
            return TO_TYPENAME.apply(superType);
        }
        String baseClass = superType.substring(0, idx);
        // to alleviate parsing issues, we split generics by | (pipe)
        TypeName[] generics =
                StreamSupport
                        .stream(PIPE.split(superType.substring(idx + 1,
                                                               superType
                                                                       .length() - 1))
                                        .spliterator(),
                                false).map(x -> {
                            if (x.indexOf('.') < 0) {
                                // generic from class
                                // e.g. T
                                return TypeVariableName.get(x);
                            }
                            return generateSuperTypeName(x);
                        }).toArray(TypeName[]::new);
        return ParameterizedTypeName.get((ClassName) TO_TYPENAME
                .apply(baseClass), generics);
    }

    private WriteInterfaces() {
    }

}
