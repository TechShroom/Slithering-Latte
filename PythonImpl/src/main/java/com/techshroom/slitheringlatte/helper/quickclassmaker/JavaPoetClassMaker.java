package com.techshroom.slitheringlatte.helper.quickclassmaker;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import autovalue.shaded.com.google.common.common.base.Throwables;
import autovalue.shaded.com.google.common.common.collect.Lists;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;
import com.google.common.primitives.Primitives;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.WildcardTypeName;
import com.techshroom.slitheringlatte.helper.active.GenerateDunderInterfaces;
import com.techshroom.slitheringlatte.helper.parsing.ClassDefinition;
import com.techshroom.slitheringlatte.helper.parsing.Parsing;
import com.techshroom.slitheringlatte.helper.parsing.Relation;
import com.techshroom.slitheringlatte.helper.resolver.ClassResolver;
import com.techshroom.slitheringlatte.helper.resolver.NameResolver;
import com.techshroom.slitheringlatte.python.annotations.MethodType;
import com.techshroom.slitheringlatte.python.annotations.MethodType.Value;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.DunderInterface;
import com.techshroom.slitheringlatte.python.interfaces.WritableAttribute;

public interface JavaPoetClassMaker<QC extends QuickClass> {

    public static final List<String> IMPORTED_PACKAGES = Lists.newArrayList(
            "java.lang", "java.util", "com.techshroom.slitheringlatte.python",
            "com.techshroom.slitheringlatte.python.annotations",
            "com.techshroom.slitheringlatte.python.interfaces");

    public static final ClassResolver CLASS_RESOLVER = ClassResolveByArray
            .create(ClassResolveByPackages.create(IMPORTED_PACKAGES,
                    PrimitveResolver.INSTANCE));
    public static final NameResolver TYPENAME_RESOLVER = NameResolverImpl
            .create(CLASS_RESOLVER);

    enum PrimitveResolver implements ClassResolver {

        INSTANCE;

        // @formatter:off
        private final Map<String, Class<?>> primitiveMap = ImmutableMap
                .<String, Class<?>> copyOf(Primitives
                        .allPrimitiveTypes()
                        .stream()
                        .collect(
                                Collectors.toMap(Class::getName,
                                        Function.identity())));

        // @formatter:on

        @Override
        public Class<?> resolve(String raw) {
            return this.primitiveMap.get(raw);
        }
    }

    final class ClassResolveByArray implements ClassResolver {

        private static final Map<String, String> fullnameToLimitedName;
        static {
            ImmutableMap.Builder<String, String> builder =
                    ImmutableMap.builder();
            builder.put("boolean", "Z");
            builder.put("byte", "B");
            builder.put("char", "C");
            builder.put("double", "D");
            builder.put("float", "F");
            builder.put("int", "I");
            builder.put("long", "J");
            builder.put("short", "S");
            fullnameToLimitedName = builder.build();
        }
        private static final Map<ClassResolver, ClassResolveByArray> cache =
                new HashMap<>();

        public static ClassResolveByArray create(ClassResolver resolver) {
            return cache.computeIfAbsent(resolver, ClassResolveByArray::new);
        }

        private final ClassResolver baseResolver;

        private ClassResolveByArray(ClassResolver resolver) {
            this.baseResolver = resolver;
        }

        @Override
        public Class<?> resolve(String raw) {
            int levels = 0;
            while (raw.endsWith("[]")) {
                levels++;
                raw = raw.substring(0, raw.length() - 2);
            }
            Class<?> resolved = this.baseResolver.resolve(raw);
            if (levels > 0) {
                String resName = resolved.getName();
                // for primitive resolution
                if (resolved.isPrimitive()) {
                    resName = fullnameToLimitedName.get(resName);
                } else {
                    resName = "L" + resName.replace('/', '.') + ";";
                }
                for (int i = 0; i < levels; i++) {
                    resName = "[" + resName;
                }
                try {
                    resolved = Class.forName(resName);
                } catch (ClassNotFoundException e) {
                    throw Throwables.propagate(e);
                }
            }
            return resolved;
        }
    }

    final class ClassResolveByPackages implements ClassResolver {

        private static final Table<Collection<String>, Optional<ClassResolver>, ClassResolveByPackages> cache =
                HashBasedTable.create();

        public static final ClassResolveByPackages create(
                Collection<String> packages) {
            return create(packages, null);
        }

        public static final ClassResolveByPackages create(
                Collection<String> packages, ClassResolver fallback) {
            Optional<ClassResolver> key = Optional.ofNullable(fallback);
            if (!cache.contains(packages, key)) {
                cache.put(packages, key, new ClassResolveByPackages(packages,
                        fallback));
            }
            return cache.get(packages, key);
        }

        private final List<String> packages;
        private final Optional<ClassResolver> fallback;

        private ClassResolveByPackages(Collection<String> packages,
                ClassResolver fallback) {
            this.packages = ImmutableList.copyOf(packages);
            this.fallback = Optional.ofNullable(fallback);
        }

        @Override
        public Class<?> resolve(String raw) {
            try {
                return Class.forName(raw);
            } catch (ClassNotFoundException ignored) {
                RuntimeException exception =
                        new IllegalStateException("no class for " + raw);
                for (String pkg : this.packages) {
                    try {
                        return Class.forName(pkg + "." + raw);
                    } catch (Throwable supressed) {
                        exception.addSuppressed(supressed);
                    }
                }
                try {
                    return this.fallback.orElseThrow(() -> exception).resolve(
                            raw);
                } catch (Exception e) {
                    if (e != exception) {
                        exception.addSuppressed(e);
                    }
                    throw exception;
                }
            }
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + this.fallback.hashCode();
            result = prime * result + this.packages.hashCode();
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ClassResolveByPackages)) {
                return false;
            }
            ClassResolveByPackages other = (ClassResolveByPackages) obj;
            return this.fallback.equals(other.fallback)
                    && this.packages.equals(other.packages);
        }

    }

    final class NameResolverImpl implements NameResolver {

        private static final Map<ClassResolver, NameResolverImpl> cache =
                new HashMap<>();

        public static NameResolverImpl create(ClassResolver classResolver) {
            return cache.computeIfAbsent(classResolver, NameResolverImpl::new);
        }

        private final ClassResolver classResolver;

        private NameResolverImpl(ClassResolver classResolver) {
            this.classResolver = classResolver;
        }

        @Override
        public TypeName resolve(String raw) {
            ClassDefinition def = null;
            if (raw.contains("[]")
                    || (def = Parsing.parseClassDefinition(raw)).getGenerics()
                            .isEmpty()) {
                // process slightly more
                try {
                    Class<?> resolved = this.classResolver.resolve(raw);
                    checkNotNull(resolved);
                    return TypeName.get(resolved);
                } catch (Exception fail) {
                    // assume strings longer than 3 characters are just
                    // non-existent classes
                    if (raw.length() > 3) {
                        // blasted hard coding
                        raw = GenerateDunderInterfaces.PACKAGE + "." + raw;
                    }
                    if (raw.indexOf('.') != -1) {
                        // try for class
                        return ClassName.bestGuess(raw);
                    } else {
                        try {
                            checkState(
                                    Character.isAlphabetic(raw.charAt(0))
                                            && raw.chars()
                                                    .skip(1)
                                                    .allMatch(
                                                            Character::isLetterOrDigit),
                                    "invalid generic %s", raw);
                        } catch (IllegalStateException ise) {
                            ise.initCause(fail);
                            throw ise;
                        }
                        return TypeVariableName.get(raw);
                    }
                }
            }
            Class<?> resolved = this.classResolver.resolve(def.getName());
            checkState(resolved == null || !resolved.isArray(),
                    "Cannot use arrays with generics");
            ClassName justClass;
            if (resolved == null) {
                System.err.println("auto-resolving " + def.getName()
                        + ", this will probably need a re-run");
                justClass = ClassName.bestGuess(def.getName());
            } else {
                justClass = ClassName.get(resolved);
            }
            TypeName[] genericBits =
                    def.getGenerics()
                            .stream()
                            .map(gen -> {
                                String gName = gen.getName();
                                TypeName[] typeNames =
                                        gen.getRelatedNames().stream()
                                                .map(ClassName::bestGuess)
                                                .toArray(TypeName[]::new);
                                if (gName.equals("?")) {
                                    checkState(typeNames.length <= 1,
                                            "Cannot have more than one bound on a wildcard");
                                    if (gen.getRelation().isPresent()) {
                                        Relation r = gen.getRelation().get();
                                        checkState(
                                                r == Relation.EXTENDS
                                                        || r == Relation.SUPER,
                                                "%s has an unknown relation %s",
                                                gen, r);
                                        if (r == Relation.EXTENDS) {
                                            return WildcardTypeName
                                                    .subtypeOf(typeNames[0]);
                                        } else {
                                            return WildcardTypeName
                                                    .supertypeOf(typeNames[0]);
                                        }
                                    } else {
                                        return WildcardTypeName
                                                .get(Object.class);
                                    }
                                } else {
                                    if (gen.getRelation().isPresent()) {
                                        Relation r = gen.getRelation().get();
                                        checkState(
                                                r == Relation.EXTENDS,
                                                "%s must have a relation of EXTENDS",
                                                gen);
                                        return TypeVariableName.get(
                                                gen.getName(), typeNames);
                                    } else {
                                        return TypeVariableName.get(gen
                                                .getName());
                                    }
                                }
                            }).toArray(TypeName[]::new);
            return ParameterizedTypeName.get(justClass, genericBits);
        }
    }

    enum Attribute implements JavaPoetClassMaker<QuickClass.Attribute> {

        INSTANCE;

        @Override
        public TypeSpec makeTypeSpec(QuickClass.Attribute qclass) {
            TypeSpec.Builder spec = parseClassDefinition(qclass);
            MethodSpec.Builder base =
                    MethodSpec
                            .methodBuilder(qclass.getName())
                            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                            .addAnnotation(
                                    AnnotationSpec
                                            .builder(MethodType.class)
                                            .addMember("value", "$T.$L",
                                                    Value.class,
                                                    qclass.getType().name())
                                            .build());
            qclass.getOriginalPythonName().ifPresent(
                    pyname -> base.addAnnotation(AnnotationSpec
                            .builder(PythonName.class)
                            .addMember("value", "$S", pyname).build()));
            TypeName type = TYPENAME_RESOLVER.resolve(qclass.getValueType());
            MethodSpec tempSpec = base.build();
            spec.addMethod(tempSpec.toBuilder().returns(type).build());
            if (qclass.isWritable()) {
                spec.addMethod(tempSpec.toBuilder()
                        .addParameter(type, qclass.getName())
                        .returns(Void.TYPE).build());
            }
            return spec.build();
        }

    }

    enum Method implements JavaPoetClassMaker<QuickClass.Method> {

        INSTANCE;

        private static final Pattern ANNOTATION_PATTERN = Pattern.compile("@("
                + Parsing.VALID_JAVA_CLASS + ")(?:\\((.+)\\))?");
        /*
         * first part looks like ANNOTATION_PATTERN but has only one general
         * matching group
         */
        private static final Pattern PARAMETER_PATTERN = Pattern
                .compile("(?:(@" + Parsing.VALID_JAVA_CLASS + "(?:\\(.+\\))?)"
                        + Parsing.SPACE + "|" + Parsing.OPTSPACE + ")" + "("
                        + Parsing.getClassMatchingPattern() + ")"
                        + Parsing.SPACE + "(" + Parsing.VALID_JAVA_IDENTIFIER
                        + ")");

        @Override
        public TypeSpec makeTypeSpec(QuickClass.Method qclass) {
            TypeSpec.Builder spec = parseClassDefinition(qclass);
            MethodSpec.Builder method =
                    MethodSpec
                            .methodBuilder(qclass.getName())
                            .addAnnotation(
                                    AnnotationSpec
                                            .builder(MethodType.class)
                                            .addMember("value", "$T.$L",
                                                    Value.class,
                                                    qclass.getType().name())
                                            .build())
                            .addModifiers(
                                    Modifier.PUBLIC,
                                    qclass.getDefaultCode().isPresent() ? Modifier.DEFAULT
                                            : Modifier.ABSTRACT)
                            .returns(
                                    TYPENAME_RESOLVER.resolve(qclass
                                            .getReturnType()));
            qclass.getOriginalPythonName().ifPresent(
                    pyname -> method.addAnnotation(AnnotationSpec
                            .builder(PythonName.class)
                            .addMember("value", "$S", pyname).build()));
            for (String param : qclass.getParameters()) {
                Matcher m = PARAMETER_PATTERN.matcher(param);
                checkState(m.matches(), "%s is not a valid parameter", param);
                String type = m.group(2);
                String name = m.group(3);
                Optional<AnnotationSpec> annotation =
                        Optional.ofNullable(m.group(1)).map(
                                this::parseAnnotation);
                TypeName resolved = TYPENAME_RESOLVER.resolve(type);
                ParameterSpec.Builder pBuilder =
                        ParameterSpec.builder(resolved, name);
                annotation.ifPresent(pBuilder::addAnnotation);
                method.addParameter(pBuilder.build());
            }
            qclass.getDefaultCode().ifPresent(method::addCode);
            spec.addMethod(method.build());
            return spec.build();
        }

        private AnnotationSpec parseAnnotation(String annot) {
            Matcher m = ANNOTATION_PATTERN.matcher(annot);
            checkState(m.matches(), "bad annotation %s", annot);
            String annotClass = m.group(1);
            Optional<String> parameters = Optional.ofNullable(m.group(2));
            AnnotationSpec.Builder builder =
                    AnnotationSpec.builder((ClassName) TYPENAME_RESOLVER
                            .resolve(annotClass));
            parameters.ifPresent(x -> {
                throw new UnsupportedOperationException(x + " params");
            });
            return builder.build();
        }
    }

    enum Mix implements JavaPoetClassMaker<QuickClass.Mix> {

        INSTANCE;

        @Override
        public TypeSpec makeTypeSpec(QuickClass.Mix qclass) {
            TypeSpec.Builder spec = parseClassDefinition(qclass);
            List<TypeName> supers =
                    qclass.getSuperInterfaces().stream()
                            .map(TYPENAME_RESOLVER::resolve)
                            .collect(Collectors.toList());
            spec.addSuperinterfaces(supers);
            return spec.build();
        }

    }

    static TypeSpec.Builder parseClassDefinition(QuickClass qclass) {
        ClassDefinition def =
                Parsing.parseClassDefinition(qclass.getClassDefinition());
        TypeSpec.Builder spec =
                TypeSpec.interfaceBuilder(def.getName())
                        .addModifiers(Modifier.PUBLIC)
                        .addTypeVariables(
                                def.getGenerics()
                                        .stream()
                                        .map(gen -> {
                                            if (gen.getRelation().isPresent()) {
                                                Relation r =
                                                        gen.getRelation().get();
                                                checkState(
                                                        r != Relation.SUPER,
                                                        "%s must have a relation of EXTENDS",
                                                        gen);
                                                return TypeVariableName.get(
                                                        gen.getName(),
                                                        gen.getRelatedNames()
                                                                .stream()
                                                                .map(ClassName::bestGuess)
                                                                .toArray(
                                                                        TypeName[]::new));
                                            } else {
                                                return TypeVariableName.get(gen
                                                        .getName());
                                            }
                                        }).collect(Collectors.toList()));
        if (qclass.getType() != Value.MIX) {
            if (qclass.getType() == Value.ATTRIBUTE
                    && ((QuickClass.Attribute) qclass).isWritable()) {
                spec.addSuperinterface(ClassName.get(WritableAttribute.class));
            } else {
                spec.addSuperinterface(ClassName.get(DunderInterface.class));
            }
        }
        return spec;
    }

    static TypeSpec makeTypeSpecByType(QuickClass qclass) {
        switch (qclass.getType()) {
            case ATTRIBUTE:
                checkState(qclass instanceof QuickClass.Attribute,
                        "invalid qclass for ATTRIBUTE");
                return Attribute.INSTANCE
                        .makeTypeSpec((QuickClass.Attribute) qclass);
            case METHOD:
                checkState(qclass instanceof QuickClass.Method,
                        "invalid qclass for METHOD");
                return Method.INSTANCE.makeTypeSpec((QuickClass.Method) qclass);
            case MIX:
                checkState(qclass instanceof QuickClass.Mix,
                        "invalid qclass for MIX");
                return Mix.INSTANCE.makeTypeSpec((QuickClass.Mix) qclass);
            default:
                throw new UnsupportedOperationException(
                        "don't know how to handle " + qclass.getType());
        }
    }

    TypeSpec makeTypeSpec(QC qclass);

}
