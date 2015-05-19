package com.techshroom.slitheringlatte.helper.active;

import java.util.ArrayList;
import java.util.List;

import com.squareup.javapoet.TypeSpec;
import com.techshroom.slitheringlatte.helper.quickclassmaker.JavaPoetClassMaker;
import com.techshroom.slitheringlatte.helper.quickclassmaker.QuickClass;

public final class GenerateDunderInterfaces {
    public static final String PACKAGE =
            "com.techshroom.slitheringlatte.python.interfaces.generated";

    private static final List<TypeSpec> registered = new ArrayList<>();

    private static QuickClass.Attribute.Builder attr() {
        return QuickClass.Attribute.builder().packageName(PACKAGE);
    }

    private static QuickClass.Method.Builder method() {
        return QuickClass.Method.builder().packageName(PACKAGE);
    }

    private static QuickClass.Mix.Builder mix() {
        return QuickClass.Mix.builder().packageName(PACKAGE);
    }

    private static TypeSpec make(QuickClass.Builder<?> qclass) {
        QuickClass built = qclass.build();
        TypeSpec spec = JavaPoetClassMaker.makeTypeSpecByType(built);
        registered.add(spec);
        return spec;
    }

    public static void main(String[] args) {
        make(method().classDefinition("Deletable")
                .pythonNameAndMethodName("__del__").returnType("void"));
        make(method().classDefinition("Representable")
                .pythonNameAndMethodName("__repr__").returnType("String[]"));
        make(method().classDefinition("ConvertableToBytes")
                .pythonNameAndMethodName("__bytes__").returnType("byte[]"));
        for (TypeSpec typeSpec : registered) {
            System.err.println(typeSpec);
        }
    }
}
