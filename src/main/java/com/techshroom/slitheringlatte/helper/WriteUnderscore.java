/*package com.techshroom.slitheringlatte.helper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Deque;
import java.util.EnumSet;
import java.util.Scanner;

import javax.lang.model.element.Modifier;

import com.google.common.base.Throwables;
import com.squareup.javawriter.JavaWriter;
import com.techshroom.slitheringlatte.array.EmptyArray;

/**
 * Tool for writing underscore files.
 * 
 * @author Kenzie Togami
 *//*
public final class WriteUnderscore {
    private static Scanner s;

    private WriteUnderscore() {
    }

    /**
     * Entry point.
     * 
     * @param args
     *            - arguments
     *//*
    public static void main(String[] args) {
        try (Scanner __ = new Scanner(System.in)) {
            s = __;
            String name = input("Name: ");
            String ret = input("Return type: ");
            String[] names = name.split(", ");
            String[] rets = ret.split(", ");
            if (names.length != rets.length) {
                throw new IllegalStateException("unequal lengths");
            }
            for (int i = 0; i < rets.length; i++) {
                String r = rets[i];
                String n = names[i];
                writeUnderscore(n, r);
            }
        }
    }

    private static final String classjd = WriteExceptionTree
            .loadFromFile("src/main/resources/underscore/class.txt");

    private static void writeUnderscore(String name, String retType) {
        String classname =
                name.substring(0, 1).toUpperCase().concat(name.substring(1))
                        .concat("Supported");
        WriteHelper helper =
                WriteHelper
                        .newInstance("src/main/java",
                                     "com.techshroom.slitheringlatte.python.underscore",
                                     classname);
        try (JavaWriter writer = helper.open()) {
            writer.emitJavadoc(classjd, name, name)
                    .beginType(classname, "interface",
                               WriteExceptionTree.PUBLIC);
            hackInStaticInterafaceMethod(writer);
            writer.beginMethod(retType, name, EnumSet.of(Modifier.STATIC),
                               "Object", "o");
            hackOutStaticInterfaceMethod(writer);
            writer.beginControlFlow("if (o instanceof %s)", classname)
                    .emitStatement("return ((%s) o).%s()", classname, name)
                    .nextControlFlow("else")
                    .emitStatement("throw new UnsupportedOperationException("
                                           + "o + %s)",
                                   JavaWriter
                                           .stringLiteral(" does not support "
                                                   + name)).endControlFlow()
                    .endMethod().emitEmptyLine()
                    .beginMethod(retType, name, EnumSet.noneOf(Modifier.class))
                    .endMethod().endType();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Field JavaWriter_scopes;
    static {
        Field tmp = null;
        try {
            tmp = JavaWriter.class.getDeclaredField("scopes");
            tmp.setAccessible(true);
        } catch (Exception e) {
            throw new Error("unacceptable");
        }
        JavaWriter_scopes = tmp;
    }

    @SuppressWarnings("unchecked")
    private static void hackInStaticInterafaceMethod(JavaWriter writer) {
        try {
            Deque<Object> reallyHacky =
                    (Deque<Object>) JavaWriter_scopes.get(writer);
            Object TypeDecleration =
                    get(JavaWriter.class.getDeclaredClasses()[0]
                            .getField("TYPE_DECLARATION"));
            reallyHacky.push(TypeDecleration);
            writer.setIndent("  ");
        } catch (Exception e) {
            throw new Error("unacceptable", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static void hackOutStaticInterfaceMethod(JavaWriter writer) {
        try {
            writer.setIndent("    ");
            Deque<Object> reallyHacky =
                    (Deque<Object>) JavaWriter_scopes.get(writer);
            Object NonAbsMethod =
                    get(JavaWriter.class.getDeclaredClasses()[0]
                            .getField("NON_ABSTRACT_METHOD"));
            Object TypeDecleration =
                    get(JavaWriter.class.getDeclaredClasses()[0]
                            .getField("TYPE_DECLARATION"));
            Object InterDecleration =
                    get(JavaWriter.class.getDeclaredClasses()[0]
                            .getField("INTERFACE_DECLARATION"));
            Object putBack = reallyHacky.pop();
            Object shouldBeType = reallyHacky.pop();
            reallyHacky.push(putBack);
            if (reallyHacky.peek() != NonAbsMethod
                    || shouldBeType != TypeDecleration
                    || reallyHacky.toArray()[1] != InterDecleration) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new Error("unacceptable", e);
        }
    }

    private static Object get(Field field) {
        field.setAccessible(true);
        try {
            return field.get(null);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw Throwables.propagate(e);
        }
    }

    private static String input() {
        return input(null);
    }

    private static String input(String message) {
        if (message != null) {
            System.out.println(message);
        } else {
            System.out.println();
        }
        return s.nextLine();
    }
}
*/