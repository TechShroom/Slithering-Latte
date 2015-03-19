package com.techshroom.slitheringlatte.ap.underscore.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.lang.model.element.Modifier;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
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
    private static final Splitter SPACE = Splitter.on(' ');

    public static void main(String[] args) {
        Path annotationData =
                RESOURCES.resolve(Paths.get("config", "annotations.txt"));
        if (!Files.exists(annotationData)) {
            return;
        }
        try (Stream<String> lines = Files.lines(annotationData)) {
            List<String> uncollapsed =
                    lines.map(String::trim)
                            .filter(x -> !(x.startsWith("#") || x.isEmpty()))
                            .collect(Collectors.toList());
            for (int i = 0; i < uncollapsed.size(); i++) {
                String current = uncollapsed.get(i);
                int startLine = i + 1;
                while (!current.endsWith(";")) {
                    i++;
                    if (i >= uncollapsed.size()) {
                        IllegalStateException throwing =
                                new IllegalStateException(
                                        "Missing semi-colon starting at line "
                                                + startLine);
                        throwing.setStackTrace(Stream
                                .concat(Arrays.asList(new StackTraceElement(
                                                              "config",
                                                              "annotations",
                                                              "annotations.txt",
                                                              startLine))
                                                .stream(),
                                        Arrays.stream(throwing.getStackTrace()))
                                .toArray(StackTraceElement[]::new));
                        throw throwing;
                    }
                    current += uncollapsed.get(i);
                }
                writeAnnotation(current.substring(0, current.length() - 1));
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

    private static void writeAnnotation(String line) {
        OptionSet lineOpts =
                PARSER.parse(StreamSupport
                        .stream(SPACE.split(line).spliterator(), false)
                        .toArray(String[]::new));
        String name = JAVA_NAME.value(lineOpts);
        boolean isWritable =
                lineOpts.has(WRITABLE) ? WRITABLE.value(lineOpts) : false;
        boolean isPseudoType = lineOpts.has(SUPERTYPES);
        TypeSpec.Builder annot =
                TypeSpec.interfaceBuilder(name).addModifiers(Modifier.PUBLIC);
        if (!isPseudoType) {
            if (lineOpts.has(PYTHON_NAME)) {
                annot.addAnnotation(AnnotationSpec.builder(PythonName.class)
                        .addMember("value", "$S", PYTHON_NAME.value(lineOpts))
                        .build());
            }
            if (isWritable) {
                annot.addSuperinterface(WRITABLE_TYPE);
            } else {
                annot.addSuperinterface(UNDERSCORE_TYPE);
            }
        } else {
            if (lineOpts.has(WRITABLE)) {
                System.err.println("Ignoring writable for name " + name);
            }
            if (lineOpts.has(PYTHON_NAME)) {
                System.err.println("Ignoring python name for name " + name);
            }
            for (String superAnnotation : SUPERTYPES.values(lineOpts)) {
                annot.addSuperinterface(ClassName.get(PACKAGE, superAnnotation));
            }
        }
        JavaFile out =
                JavaFile.builder(PACKAGE, annot.build()).indent("    ")
                        .skipJavaLangImports(true).addFileComment("").build();
        try {
            out.writeTo(CODE);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed on " + line);
        }
    }

    private WriteAnnotations() {
    }

}
