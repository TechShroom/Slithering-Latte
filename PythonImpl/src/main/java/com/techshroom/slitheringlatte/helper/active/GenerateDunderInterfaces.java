package com.techshroom.slitheringlatte.helper.active;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import com.abdulfatir.jcomplexnumber.ComplexNumber;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ObjectArrays;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.techshroom.slitheringlatte.helper.HelperConstants;
import com.techshroom.slitheringlatte.helper.parsing.ClassDefinition;
import com.techshroom.slitheringlatte.helper.parsing.Parsing;
import com.techshroom.slitheringlatte.helper.quickclassmaker.JavaPoetClassMaker;
import com.techshroom.slitheringlatte.helper.quickclassmaker.QuickClass;
import com.techshroom.slitheringlatte.helper.quickclassmaker.QuickClass.Method.Builder;
import com.techshroom.slitheringlatte.python.annotations.MethodType.Value;
import com.techshroom.slitheringlatte.python.error.NotImplementedError;

public final class GenerateDunderInterfaces {
    public static final String PACKAGE =
            "com.techshroom.slitheringlatte.python.interfaces.generated";

    private static final List<TypeSpec> registered = new ArrayList<>();

    private static final CodeBlock OPERATOR_UNIMPLEMENTED_CODE = CodeBlock
            .builder()
            .addStatement("throw new $T()", NotImplementedError.class).build();

    private static QuickClass.Attribute.Builder attr() {
        return QuickClass.Attribute.builder().packageName(PACKAGE);
    }

    private static QuickClass.Method.Builder method() {
        return QuickClass.Method.builder().packageName(PACKAGE);
    }

    private static QuickClass.Mix.Builder mix() {
        return QuickClass.Mix.builder().packageName(PACKAGE);
    }

    private static TypeSpec make(QuickClass.Builder<?, ?> builder) {
        ClassDefinition cDef =
                Parsing.parseClassDefinition(builder.getClassDefinition());
        return makeClass(cDef, builder);
    }

    private static TypeSpec makeClass(String name,
            QuickClass.Builder<?, ?> first, QuickClass.Builder<?, ?>... rest) {
        return makeClass(ClassDefinition.create(name, ImmutableList.of()),
                first, rest);
    }

    private static TypeSpec makeClass(ClassDefinition name,
            QuickClass.Builder<?, ?> first, QuickClass.Builder<?, ?>... rest) {
        QuickClass.Builder<?, ?>[] all = ObjectArrays.concat(first, rest);
        TypeSpec.Builder builder =
                JavaPoetClassMaker.parseClassDefinition(first.classDefinition(
                        name.toRawDefinition()).build());
        for (QuickClass.Builder<?, ?> qclass : all) {
            qclass.classDefinition(name.toRawDefinition());
            QuickClass built = qclass.build();
            TypeSpec spec = JavaPoetClassMaker.makeTypeSpecByType(built);
            if (built.getType() == Value.MIX) {
                builder.addSuperinterfaces(spec.superinterfaces);
            }
            builder.addMethods(spec.methodSpecs);
        }
        TypeSpec built = builder.build();
        registered.add(built);
        return built;
    }

    public static void main(String[] args) {
        addPythonBasics();
        addOperatorInterfaces();
        addNumberConversionInterfaces();
        addCallableInterfaces();
        addBoundCallableInterfaces();
        addBuiltinMethodInterfaces();
        addBoundBuiltinMethodInterfaces();
        addMetaClassInterfaces();
        addModuleInterfaces();
        addFormatInterfaces();
        addContextManagerInterfaces();
        for (TypeSpec typeSpec : registered) {
            JavaFile file =
                    JavaFile.builder(PACKAGE, typeSpec).indent("    ")
                            .skipJavaLangImports(false).build();
            try {
                file.writeTo(HelperConstants.src.main.java.path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addPythonBasics() {
        make(method().classDefinition("Deletable")
                .pythonNameAndMethodName("__del__").returnType("void"));
        make(method().classDefinition("Representable")
                .pythonNameAndMethodName("__repr__").returnType("String"));
        make(method().classDefinition("ConvertableToBytes")
                .pythonNameAndMethodName("__bytes__").returnType("byte[]"));
        make(method().classDefinition("ConvertableToBool")
                .pythonNameAndMethodName("__bool__").returnType("boolean"));
        addComparables();
        makeClass(
                "AttributeHolder",
                method().pythonNameAndMethodName("__getattribute__")
                        .addParameter("String key").returnType("Object"),
                method().pythonNameAndMethodName("__setattr__")
                        .addParameters("String key", "Object value")
                        .returnType("Object"),
                method().pythonNameAndMethodName("__delattr__")
                        .addParameter("String key").returnType("void"),
                method().pythonNameAndMethodName("__dir__")
                        .returnType("Object"),
                attr().pythonNameAndMethodName("__slots__").valueType(
                        "List<String>"),
                attr().pythonNameAndMethodName("__dict__")
                        .valueType("Map<String, Object>").writable(true));
        make(mix().classDefinition("PythonObject<T>").addSuperInterfaces(
                "Deletable", "Representable", "ConvertableToBytes",
                "ComparableMixin<T>", "ConvertableToBool", "AttributeHolder"));
        make(method().classDefinition("MissingAttributeProvider")
                .pythonNameAndMethodName("__getattr__")
                .addParameter("String key").returnType("Object"));
    }

    private static void addOperatorInterfaces() {
        String[][] prefixes = { { "", "" }, { "R", "r" }, { "I", "i" } };
        Stream<String[][]> binaryOps =
                Stream.of("add", "sub", "mul", "truediv", "floordiv", "mod",
                        "divmod", "pow", "lshift", "rshift", "and", "xor", "or")
                        .flatMap(
                                x -> Stream.of(prefixes).map(
                                        s -> new String[][] { s,
                                                new String[] { x } }));
        Stream<String[][]> unaryOps =
                Stream.of("neg", "pos", "add", "invert", "round")
                        .map(x -> new String[][] { prefixes[0],
                                new String[] { x } });
        Stream.concat(binaryOps, unaryOps).forEachOrdered(
                GenerateDunderInterfaces::$doCreateOperatorInterface);
    }

    private static void addNumberConversionInterfaces() {
        Map<String, String> conv =
                ImmutableMap.of("complex", ComplexNumber.class.getName(),
                        "int", "int", "float", "double", "index", "int");
        conv.entrySet()
                .stream()
                .forEachOrdered(
                        pair -> {
                            String x = pair.getKey();
                            String operatorBase =
                                    Character.toUpperCase(x.charAt(0))
                                            + x.substring(1);
                            Builder method =
                                    method().classDefinition(
                                            "ConvertableTo" + operatorBase)
                                            .originalPythonName("__" + x + "__")
                                            .name(x)
                                            .returnType(pair.getValue())
                                            .defaultCode(
                                                    OPERATOR_UNIMPLEMENTED_CODE);
                            try {
                                make(method);
                            } catch (IllegalArgumentException e) {
                                if (!e.getMessage().startsWith(
                                        "not a valid name")) {
                                    throw e;
                                }
                                // this means we have a bad method name
                                make(method.name(x + "$"));
                            }
                        });
    }

    private static void $doCreateOperatorInterface(String[][] op) {
        String operatorBase;
        String opInput = op[1][0];
        String opPrefixUpper = op[0][0];
        String opPrefixLower = op[0][1];
        if (opInput.endsWith("shift")) {
            String first = opInput.substring(0, 2);
            operatorBase = first.toUpperCase() + opInput.substring(2);
        } else {
            operatorBase =
                    Character.toUpperCase(opInput.charAt(0))
                            + opInput.substring(1);
        }
        String operatorTitleCase = opPrefixUpper + operatorBase;
        String operatorCamelCase;
        if (opPrefixLower.isEmpty()) {
            operatorCamelCase = opInput;
        } else {
            operatorCamelCase = opPrefixLower + operatorBase;
        }
        make(method().classDefinition("Operator" + operatorTitleCase)
                .originalPythonName("__" + opPrefixLower + opInput + "__")
                .name(operatorCamelCase).addParameter("Object other")
                .returnType("Object").defaultCode(OPERATOR_UNIMPLEMENTED_CODE));
    }

    private static void addComparables() {
        Map<String, String> names =
                new ImmutableMap.Builder<String, String>()
                        .put("lt", "lessThan").put("le", "lessThanOrEqualTo")
                        .put("eq", "equalTo").put("ne", "notEqualTo")
                        .put("gt", "greaterThan")
                        .put("ge", "greaterThanOrEqualTo").build();
        for (Entry<String, String> e : names.entrySet()) {
            make(method()
                    .classDefinition(
                            "Comparable" + e.getKey().toUpperCase() + "<T>")
                    .addParameter("T other")
                    .originalPythonName("__" + e.getKey() + "__")
                    .name(e.getValue()).returnType("boolean"));
        }
    }

    private static void addCallableInterfaces() {
        makeClass("CallableType", attr().pythonNameAndMethodName("__doc__")
                .writable(true).valueType("String"),
                attr().pythonNameAndMethodName("__name__").writable(true)
                        .valueType("String"),
                attr().pythonNameAndMethodName("__qualname__").writable(true)
                        .valueType("String"),
                attr().pythonNameAndMethodName("__module__").writable(true)
                        .valueType("String"),
                attr().pythonNameAndMethodName("__defaults__").writable(true)
                        .valueType("Tuple"),
                attr().pythonNameAndMethodName("__code__").writable(true)
                        .valueType("String"),
                attr().pythonNameAndMethodName("__global__").writable(true)
                        .valueType("Map<String, Object>"), mix()
                        .addSuperInterface("AttributeHolder"), attr()
                        .pythonNameAndMethodName("__closure__").writable(true)
                        .valueType("Tuple"),
                attr().pythonNameAndMethodName("__annotations__")
                        .writable(true).valueType("Map<String, Object>"),
                attr().pythonNameAndMethodName("__kwdefaults__").writable(true)
                        .valueType("Map<String, Object>"));
    }

    private static void addBoundCallableInterfaces() {
        makeClass("BoundCallableType",
                attr().pythonNameAndMethodName("__self__").writable(true)
                        .valueType("Object"),
                attr().pythonNameAndMethodName("__func__").writable(true)
                        .valueType("Object"),
                mix().addSuperInterface("CallableType"));
    }

    private static void addBuiltinMethodInterfaces() {
        makeClass("BuiltinMethodType",
                attr().pythonNameAndMethodName("__doc__").writable(true)
                        .valueType("String"),
                attr().pythonNameAndMethodName("__name__").writable(true)
                        .valueType("String"),
                attr().pythonNameAndMethodName("__module__").writable(true)
                        .valueType("String"));
    }

    private static void addBoundBuiltinMethodInterfaces() {
        makeClass("BoundBuiltinMethodType",
                attr().pythonNameAndMethodName("__self__").writable(true)
                        .valueType("Object"),
                mix().addSuperInterface("BuiltinMethodType"));
    }

    private static void addMetaClassInterfaces() {
        makeClass(
                "Metaclass",
                method().pythonNameAndMethodName("__prepare__")
                        .addParameters("String name", "List<String> bases",
                                "@KeywordArgs Map<String, Object> kwargs")
                        .defaultCode(
                                CodeBlock
                                        .builder()
                                        .add("return new java.util.HashMap<>();\n")
                                        .build())
                        .returnType("Map<String, Object>"), method()
                        .pythonNameAndMethodName("__instancecheck__")
                        .addParameter("String instance").returnType("boolean"),
                method().pythonNameAndMethodName("__subclasscheck__")
                        .addParameter("String subclass").returnType("boolean"),
                mix().addSuperInterface("CallableType"));
    }

    private static void addModuleInterfaces() {
        makeClass(
                "Module",
                attr().pythonNameAndMethodName("__doc__").writable(true)
                        .valueType("String"),
                attr().pythonNameAndMethodName("__name__").writable(true)
                        .valueType("String"),
                mix().addSuperInterface("AttributeHolder"),
                attr().classDefinition("FileSource")
                        .pythonNameAndMethodName("__file__").writable(true)
                        .valueType("String"));
    }

    private static void addFormatInterfaces() {
        make(method().classDefinition("Formattable")
                .pythonNameAndMethodName("__format__")
                .addParameter("String formatSpec")
                .returnType("String"));
    }

    private static void addContextManagerInterfaces() {
        makeClass(
                "ContextManager",
                method().pythonNameAndMethodName("__enter__").returnType(
                        "Object"), method().pythonNameAndMethodName("__exit__")
                        .addParameter("Exception raised").returnType("boolean"));
    }

}
