package com.techshroom.slitheringlatte.helper.active;

import java.io.IOException;
import java.util.Set;

import javax.lang.model.element.Modifier;

import com.google.common.collect.Sets;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.techshroom.slitheringlatte.helper.HelperConstants;
import com.techshroom.slitheringlatte.logging.DefaultLevel;
import com.techshroom.slitheringlatte.logging.Level;

public final class GenerateMessageMethods {

    private static final String PACKAGE = Level.class.getPackage().getName();
    private static final Set<Modifier> NOT_IMPLEMENTED_MODS = Sets
            .immutableEnumSet(Modifier.PUBLIC, Modifier.ABSTRACT);
    private static final Set<Modifier> IMPLEMENTED_MODS = Sets
            .immutableEnumSet(Modifier.PUBLIC, Modifier.DEFAULT);

    private GenerateMessageMethods() {
    }

    public static void main(String[] args) {
        TypeSpec.Builder spec = TypeSpec.interfaceBuilder("MessageLogger");
        addLogMethod(spec);
        addLogExceptionMethod(spec);
        for (Level level : DefaultLevel.PRINTING_LEVELS) {
            addLogLevelMethod(spec, level);
        }
        try {
            JavaFile.builder(PACKAGE, spec.build()).indent("    ")
                    .skipJavaLangImports(true).build()
                    .writeTo(HelperConstants.src.main.java.path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addLogMethod(TypeSpec.Builder spec) {
        spec.addMethod(MethodSpec.methodBuilder("log")
                .addModifiers(NOT_IMPLEMENTED_MODS).returns(void.class)
                .addParameter(Level.class, "level")
                .addParameter(Object.class, "message").build());
    }

    private static void addLogExceptionMethod(TypeSpec.Builder spec) {
        spec.addMethod(MethodSpec.methodBuilder("logException")
                .addModifiers(NOT_IMPLEMENTED_MODS).returns(void.class)
                .addParameter(Exception.class, "exception").build());
    }

    private static void addLogLevelMethod(TypeSpec.Builder spec, Level level) {
        spec.addMethod(MethodSpec.methodBuilder(level.toString().toLowerCase())
                .addModifiers(IMPLEMENTED_MODS).returns(void.class)
                .addParameter(Object.class, "message")
                .addStatement("log($T.$L, message)", DefaultLevel.class, level)
                .build());
    }

}
