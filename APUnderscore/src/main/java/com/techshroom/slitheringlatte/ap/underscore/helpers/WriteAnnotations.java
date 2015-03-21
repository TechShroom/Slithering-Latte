package com.techshroom.slitheringlatte.ap.underscore.helpers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
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
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.ap.underscore.interfaces.DunderAttribute;
import com.techshroom.slitheringlatte.ap.underscore.interfaces.Writable;

/**
 * Writes all annotations defined in the main resource,s
 * {@code config/annotations.txt}.
 * 
 * @author Kenzie Togami
 */
final class WriteAnnotations {
    private static final Path BASE = Paths.get("src", "main");
    private static final Path RESOURCES = BASE.resolve("resources");
    private static final Path CODE = BASE.resolve("java");
    private static final Path ANNOTATION_SRC = RESOURCES.resolve(Paths
            .get("config", "annotations.txt"));
    private static final Splitter SPACE = Splitter.on(' ');

    private static void modExceptionTraceWithLine(Exception e, int line) {
        StackTraceElement[] original = e.getStackTrace();
        StackTraceElement[] expanded =
                new StackTraceElement[original.length + 1];
        System.arraycopy(original, 0, expanded, 1, original.length);
        expanded[0] =
                new StackTraceElement("config/annotations", "txt",
                        ANNOTATION_SRC.toString().replace(File.separatorChar,
                                                          '/'), line);
        e.setStackTrace(expanded);
    }

    public static void main(String[] args) {
        if (!Files.exists(ANNOTATION_SRC)) {
            return;
        }
        try (Stream<String> lines = Files.lines(ANNOTATION_SRC)) {
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
                writeAnnotation(current.substring(0, current.length() - 1),
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
            "com.techshroom.slitheringlatte.ap.underscore.interfaces";

    // parser converters
    private static final ValueConverter<Class<?>> TO_CLASS =
            new ClassConverter();

    private static final class ClassConverter implements
            ValueConverter<Class<?>> {
        private final List<String> importedPackages = ImmutableList
                .of("java.lang", "com.techshroom.slitheringlatte.python");

        @Override
        public Class<?> convert(String value) {
            try {
                return Class.forName(value);
            } catch (ClassNotFoundException e) {
                // try again with java.lang
                Supplier<RuntimeException> exec =
                        () -> new IllegalArgumentException(value, e);
                if (value.indexOf('.') < 0) {
                    return importedPackages.stream()
                            .map(pack -> pack + '.' + value)
                            .map(this::errorFreeConvert)
                            .filter(Objects::nonNull).findFirst()
                            .orElseThrow(exec);
                } else {
                    throw exec.get();
                }
            }
        }

        private final Class<?> errorFreeConvert(String value) {
            try {
                return Class.forName(value);
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

    // some parsing info...
    private static final OptionParser PARSER = new OptionParser();
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
    private static final ArgumentAcceptingOptionSpec<String> ATTR_METHOD_NAME =
            PARSER.acceptsAll(ImmutableList.of("a", "attribute-func-name"),
                              "Name for the getter/setter function")
                    .requiredUnless(PYTHON_NAME, SUPERTYPES).withRequiredArg();
    private static final ArgumentAcceptingOptionSpec<Class<?>> ATTR_TYPE =
            PARSER.acceptsAll(ImmutableList.of("t", "attribute-type"),
                              "The type of the attribute")
                    .requiredUnless(SUPERTYPES).withRequiredArg()
                    .withValuesConvertedBy(TO_CLASS);
    private static final ArgumentAcceptingOptionSpec<String> ATTR_TYPE_GENERIC =
            PARSER.acceptsAll(ImmutableList.of("g", "attribute-generic"),
                              "The generic for the type of the attribute")
                    .withRequiredArg().defaultsTo(new String[] {});

    private static <T> Optional<T> argOpt(OptionSpec<T> opt, OptionSet opts) {
        return Optional.ofNullable(opts.valueOf(opt));
    }

    private static void writeAnnotation(String line, int lineNumber) {
        try {
            OptionSet lineOpts =
                    PARSER.parse(StreamSupport.stream(SPACE.split(line)
                                                              .spliterator(),
                                                      false)
                            .toArray(String[]::new));
            String name = JAVA_NAME.value(lineOpts);
            boolean isWritable =
                    lineOpts.has(WRITABLE) ? WRITABLE.value(lineOpts) : false;
            boolean isPseudoType = lineOpts.has(SUPERTYPES);
            TypeSpec.Builder iface =
                    TypeSpec.interfaceBuilder(name)
                            .addModifiers(Modifier.PUBLIC);
            if (!isPseudoType) {
                if (lineOpts.has(PYTHON_NAME)) {
                    iface.addAnnotation(AnnotationSpec
                            .builder(PythonName.class)
                            .addMember("value",
                                       "$S",
                                       PYTHON_NAME.value(lineOpts)).build());
                }

                String methodName =
                        argOpt(ATTR_METHOD_NAME, lineOpts)
                                .orElseGet(argOpt(PYTHON_NAME, lineOpts)::get);
                Supplier<MethodSpec.Builder> base =
                        () -> MethodSpec
                                .methodBuilder(methodName)
                                .addModifiers(Modifier.PUBLIC, Modifier.DEFAULT);
                TypeName attributeType = generateTypeName(lineOpts);
                if (isWritable) {
                    iface.addSuperinterface(WRITABLE_TYPE);
                    MethodSpec setter =
                            base.get()
                                    .addParameter(attributeType, methodName)
                                    .addCode("throw new $T($S);\n",
                                             UnsupportedOperationException.class,
                                             methodName + " not implemented")
                                    .build();
                    iface.addMethod(setter);
                } else {
                    iface.addSuperinterface(UNDERSCORE_TYPE);
                }
                MethodSpec getter =
                        base.get().returns(attributeType)
                                .addCode("return null;\n").build();
                iface.addMethod(getter);
            } else {
                Stream<Entry<OptionSpec<?>, List<?>>> optStream =
                        lineOpts.asMap()
                                .entrySet()
                                .stream()
                                .filter(e -> e.getKey() != SUPERTYPES
                                        && e.getKey() != JAVA_NAME
                                        && lineOpts.has(e.getKey()))
                                .onClose(() -> System.err.println("==Processing "
                                        + name + "=="));
                optStream.forEach(e -> System.err.println("Ignoring "
                        + e.getKey() + "=" + e.getValue()));
                optStream.close();
                for (String superAnnotation : SUPERTYPES.values(lineOpts)) {
                    iface.addSuperinterface(ClassName.get(PACKAGE,
                                                          superAnnotation));
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

    private static TypeName generateTypeName(OptionSet lineOpts) {
        Class<?> attrType = ATTR_TYPE.value(lineOpts);
        if (!lineOpts.has(ATTR_TYPE_GENERIC)) {
            return ClassName.get(attrType);
        }
        List<String> generic = ATTR_TYPE_GENERIC.values(lineOpts);
        return ParameterizedTypeName.get(attrType,
                                         generic.stream()
                                                 .map(TO_CLASS::convert)
                                                 .toArray(Type[]::new));
    }

    private WriteAnnotations() {
    }

}
