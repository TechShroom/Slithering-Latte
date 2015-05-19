package com.techshroom.slitheringlatte.helper.quickclassmaker;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import autovalue.shaded.com.google.common.common.base.Throwables;
import autovalue.shaded.com.google.common.common.collect.Lists;

import com.google.common.base.Splitter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;
import com.google.common.primitives.Primitives;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import com.techshroom.slitheringlatte.helper.parsing.ClassDefinition;
import com.techshroom.slitheringlatte.helper.parsing.Parsing;
import com.techshroom.slitheringlatte.helper.parsing.Relation;
import com.techshroom.slitheringlatte.helper.resolver.ClassResolver;
import com.techshroom.slitheringlatte.helper.resolver.NameResolver;
import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.InterfaceType.Value;
import com.techshroom.slitheringlatte.python.annotations.PythonName;

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
                .<String, Class<?>> copyOf(Primitives.allPrimitiveTypes()
                        .stream().collect(Collectors.toMap(Class::getName,
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
                    } catch (ClassNotFoundException supressed) {
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
            TypeName name = null;
            try {
                Class<?> resolved = this.classResolver.resolve(raw);
                checkNotNull(resolved);
                name = ClassName.get(resolved);
            } catch (Exception fail) {
                if (raw.indexOf('.') != -1) {
                    // try for class
                    name = ClassName.bestGuess(raw);
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
                    name = TypeVariableName.get(raw);
                }
            }
            return name;
        }

    }

    enum Attribute implements JavaPoetClassMaker<QuickClass.Attribute> {

        INSTANCE;

        @Override
        public TypeSpec makeTypeSpec(QuickClass.Attribute qclass) {
            TypeSpec.Builder spec = parseClassDefinition(qclass);
            MethodSpec base =
                    MethodSpec.methodBuilder(qclass.getName())
                            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                            .build();
            spec.addMethod(base.toBuilder()
                    .returns(TYPENAME_RESOLVER.resolve(qclass.getValueType()))
                    .build());
            return spec.build();
        }

    }

    enum Method implements JavaPoetClassMaker<QuickClass.Method> {

        INSTANCE;

        private static final Splitter SPACE = Splitter.on(' ');

        @Override
        public TypeSpec makeTypeSpec(QuickClass.Method qclass) {
            TypeSpec.Builder spec = parseClassDefinition(qclass);
            MethodSpec.Builder method =
                    MethodSpec
                            .methodBuilder(qclass.getName())
                            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                            .returns(
                                    TYPENAME_RESOLVER.resolve(qclass
                                            .getReturnType()));
            for (String param : qclass.getParameters()) {
                List<String> splits = SPACE.splitToList(param);
                checkState(splits.size() == 2, "bad parameter %s", param);
                String type = splits.get(0);
                String name = splits.get(1);
                TypeName resolved = TYPENAME_RESOLVER.resolve(type);
                method.addParameter(resolved, name);
            }
            spec.addMethod(method.build());
            return spec.build();
        }

    }

    enum Mix implements JavaPoetClassMaker<QuickClass.Mix> {

        INSTANCE;

        @Override
        public TypeSpec makeTypeSpec(QuickClass.Mix qclass) {
            TypeSpec.Builder spec = parseClassDefinition(qclass);
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
                                                                .map(name -> ClassName
                                                                        .bestGuess(name))
                                                                .toArray(
                                                                        TypeName[]::new));
                                            } else {
                                                return TypeVariableName.get(gen
                                                        .getName());
                                            }
                                        }).collect(Collectors.toList()))
                        .addAnnotation(
                                AnnotationSpec
                                        .builder(InterfaceType.class)
                                        .addMember("value", "$T.$L",
                                                Value.class,
                                                qclass.getType().name())
                                        .build());
        qclass.getOriginalPythonName().ifPresent(
                pyname -> spec.addAnnotation(AnnotationSpec
                        .builder(PythonName.class)
                        .addMember("value", "$S", pyname).build()));
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
