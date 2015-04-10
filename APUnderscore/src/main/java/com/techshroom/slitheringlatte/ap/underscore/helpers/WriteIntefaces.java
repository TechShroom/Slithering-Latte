package com.techshroom.slitheringlatte.ap.underscore.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
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
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Primitives;
import com.squareup.javapoet.*;
import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType.Value;
import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.ap.underscore.interfaces.DunderAttribute;
import com.techshroom.slitheringlatte.ap.underscore.interfaces.Writable;
import com.techshroom.slitheringlatte.array.EmptyArray;

/**
 * Writes all annotations defined in the main resources
 * {@code config/annotations.txt}.
 * 
 * @author Kenzie Togami
 */
final class WriteIntefaces {

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
            "com.techshroom.slitheringlatte.ap.underscore.interfaces.generated";

    // parser converters
    private static final ValueConverter<Class<?>> TO_CLASS =
            new ClassConverter();

    private static final Function<String, Class<?>> TO_CLASS_COMMON =
            TO_CLASS::convert;

    private static final Function<String, ClassName> TO_CLASSNAME = x -> {
        try {
            return ClassName.get(TO_CLASS_COMMON.apply(x));
        } catch (IllegalArgumentException ignored) {
            if (x.indexOf('.') < 0) {
                return ClassName.get(PACKAGE, x);
            }
            return ClassName.bestGuess(x);
        }
    };

    private static final class ClassConverter implements
            ValueConverter<Class<?>> {
        private final List<String> importedPackages = ImmutableList
                .of("java.lang",
                    "com.techshroom.slitheringlatte.python",
                    "com.techshroom.slitheringlatte.ap.underscore.interfaces",
                    PACKAGE);

        @Override
        public Class<?> convert(String value) {
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
                    return importedPackages.stream()
                            .map(pack -> pack + '.' + value)
                            .map(this::errorFreeConvert)
                            .filter(Objects::nonNull).findFirst()
                            .orElseThrow(() -> execWithCause.apply(e));
                } else {
                    throw execWithCause.apply(e);
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
                ATTR_TYPE, GENERIC_SHARED);

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
    private static final ArgumentAcceptingOptionSpec<Class<?>> ATTR_TYPE =
            PARSER.acceptsAll(ImmutableList.of("t", "attribute-type"),
                              "The type of the attribute")
                    .requiredUnless(SUPERTYPES).withRequiredArg()
                    .withValuesConvertedBy(TO_CLASS);
    private static final ArgumentAcceptingOptionSpec<String> GENERIC = PARSER
            .acceptsAll(ImmutableList.of("g", "attribute-generic"),
                        "The generic for the type of the attribute")
            .withRequiredArg().withValuesSeparatedBy(',')
            .defaultsTo(new String[] {});
    private static final ArgumentAcceptingOptionSpec<Boolean> GENERIC_SHARED =
            PARSER.acceptsAll(ImmutableList.of("share-generic"),
                              "Boolean value for sharing the generic of the type.")
                    .withOptionalArg().ofType(Boolean.class).defaultsTo(true);
    private static final ArgumentAcceptingOptionSpec<String> PARAMETERS =
            PARSER.acceptsAll(ImmutableList.of("m", "parameters"),
                              "The parameters for the method")
                    .withRequiredArg().withValuesSeparatedBy(',')
                    .defaultsTo(new String[] {});

    private static <T> Optional<T> argOpt(OptionSpec<T> opt, OptionSet opts) {
        return Optional.ofNullable(opts.valueOf(opt));
    }

    private static void writeInterface(String line, int lineNumber) {
        try {
            OptionSet lineOpts =
                    PARSER.parse(StreamSupport.stream(SPACE.split(line)
                                                              .spliterator(),
                                                      false)
                            .toArray(String[]::new));
            String name = JAVA_NAME.value(lineOpts);
            List<String> generics = GENERIC.values(lineOpts);
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
            if (type == IType.MIX
                    || (lineOpts.has(GENERIC_SHARED) && GENERIC_SHARED
                            .value(lineOpts))) {
                iface.addTypeVariables(generics.stream()
                        .map(TypeVariableName::get)
                        .collect(Collectors.toList()));
            }
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
                                    MethodSpec.methodBuilder(methodName)
                                            .addModifiers(Modifier.PUBLIC,
                                                          Modifier.ABSTRACT);
                            if (lineOpts.has(OVERRIDES)
                                    && OVERRIDES.value(lineOpts)) {
                                b.addAnnotation(Override.class);
                            }
                            return b;
                        };
                TypeName returnType = generateTypeName(lineOpts);
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
                                            .map(param -> {
                                                String[] data =
                                                        param.split(":");
                                                TypeName typeName = null;
                                                try {
                                                    typeName =
                                                            TO_CLASSNAME.apply(data[0]);
                                                } catch (IllegalArgumentException ignore) {
                                                    typeName =
                                                            TypeVariableName
                                                                    .get(data[0]);
                                                }
                                                return ParameterSpec
                                                        .builder(typeName,
                                                                 data[1])
                                                        .build();
                                            }).collect(Collectors.toList()))
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

    private static TypeName generateSuperTypeName(String superType) {
        int idx = superType.indexOf('<');
        if (idx < 0) {
            return TO_CLASSNAME.apply(superType);
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
                            return TO_CLASSNAME.apply(x);
                        }).toArray(TypeName[]::new);
        return ParameterizedTypeName.get(TO_CLASSNAME.apply(baseClass),
                                         generics);
    }

    private static TypeName generateTypeName(OptionSet lineOpts) {
        Class<?> attrType = ATTR_TYPE.value(lineOpts);
        int arrayLevel = 0;
        while (attrType.isArray()) {
            arrayLevel++;
            attrType = attrType.getComponentType();
        }
        TypeName res = null;
        if (attrType.isPrimitive()) {
            res = TypeName.get(attrType);
        } else {
            ClassName cls = ClassName.get(attrType);
            if (!lineOpts.has(GENERIC)) {
                res = ClassName.get(attrType);
            } else {
                TypeVariableName[] generics =
                        GENERIC.values(lineOpts).stream()
                                .map(TypeVariableName::get)
                                .toArray(TypeVariableName[]::new);
                res = ParameterizedTypeName.get(cls, generics);
            }
        }
        while (arrayLevel > 0) {
            res = ArrayTypeName.of(res);
            arrayLevel--;
        }
        return res;
    }

    private WriteIntefaces() {
    }

}
