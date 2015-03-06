package com.techshroom.slitheringlatte.ap.underscore.helpers;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.techshroom.slitheringlatte.ap.underscore.annotations.UnderscoreAttribute;
import com.techshroom.slitheringlatte.ap.underscore.annotations.Writable;

/**
 * Writes all annotations defined in the main resource,s
 * {@code config/annotations.txt}.
 * 
 * @author Kenzie Togami
 */
public final class WriteAnnotations {
    private static final Path BASE = Paths.get("src", "main");
    private static final Path RESOURCES = BASE.resolve("resources");
    private static final Path CODE = BASE.resolve("java");
    private static final Splitter SPACE = Splitter.on(' ');

    @SuppressWarnings("javadoc")
    public static void main(String[] args) {
        Path annotationData =
                RESOURCES.resolve(Paths.get("config", "annotations.txt"));
        if (!Files.exists(annotationData)) {
            return;
        }
        try (Stream<String> lines = Files.lines(annotationData)) {
            lines.map(String::trim)
                    .filter(x -> !(x.startsWith("#") || x.isEmpty()))
                    .forEach(WriteAnnotations::writeAnnotation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final AnnotationSpec TARGET_ANNOTATION = AnnotationSpec
            .builder(Target.class)
            .addMember("value",
                       "$T.$N",
                       ElementType.class,
                       FieldSpec.builder(ElementType.class, "ANNOTATION_TYPE")
                               .build())
            .addMember("value", "$T.$N", ElementType.class,
                       FieldSpec.builder(ElementType.class, "METHOD").build())
            .build();
    private static final AnnotationSpec RETENTION_ANNOTATION = AnnotationSpec
            .builder(Retention.class)
            .addMember("value",
                       "$T.$N",
                       RetentionPolicy.class,
                       FieldSpec.builder(RetentionPolicy.class, "SOURCE")
                               .build()).build();
    private static final AnnotationSpec WRITABLE_ANNOTATION = AnnotationSpec
            .builder(Writable.class).build();
    private static final AnnotationSpec UNDERSCORE_ANNOTATION = AnnotationSpec
            .builder(UnderscoreAttribute.class).build();
    private static final String PACKAGE =
            "com.techshroom.slitheringlatte.ap.underscore.annotations";

    // some parsing info...
    private static final OptionParser PARSER = new OptionParser();
    private static final ArgumentAcceptingOptionSpec<String> JAVA_NAME = PARSER
            .acceptsAll(ImmutableList.of("n", "name"),
                        "Name of the Java annotation.").withRequiredArg();
    private static final ArgumentAcceptingOptionSpec<String> PYTHON_NAME =
            PARSER.acceptsAll(ImmutableList.of("p", "python-name"),
                              "Name of the Java annotation.").withRequiredArg();
    private static final ArgumentAcceptingOptionSpec<Boolean> WRITABLE = PARSER
            .acceptsAll(ImmutableList.of("w", "writable"),
                        "Boolean value for writing.").withOptionalArg()
            .ofType(Boolean.class).defaultsTo(true);
    private static final ArgumentAcceptingOptionSpec<String> SUPERTYPES =
            PARSER.acceptsAll(ImmutableList.of("s", "supertype"),
                              "'Supertypes' of annotation, "
                                      + "actually just applied meta-annotations.")
                    .withRequiredArg().withValuesSeparatedBy(',')
                    .defaultsTo(new String[0]);

    private static void writeAnnotation(String line) {
        OptionSet lineOpts =
                PARSER.parse(StreamSupport
                        .stream(SPACE.split(line).spliterator(), false)
                        .toArray(String[]::new));
        String name = JAVA_NAME.value(lineOpts);
        boolean isWritable =
                lineOpts.has(WRITABLE) ? WRITABLE.value(lineOpts) : false;
        TypeSpec.Builder annot =
                TypeSpec.annotationBuilder(name).addModifiers(Modifier.PUBLIC);
        annot.addAnnotation(TARGET_ANNOTATION);
        annot.addAnnotation(RETENTION_ANNOTATION);
        AnnotationSpec.Builder underscore = UNDERSCORE_ANNOTATION.toBuilder();
        if (lineOpts.has(PYTHON_NAME)) {
            underscore.addMember("pythonName", "$S",
                                 PYTHON_NAME.value(lineOpts));
        }
        annot.addAnnotation(underscore.build());
        if (isWritable) {
            annot.addAnnotation(WRITABLE_ANNOTATION);
        }
        for (String superAnnotation : SUPERTYPES.values(lineOpts)) {
            annot.addAnnotation(ClassName.get(PACKAGE, superAnnotation));
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
