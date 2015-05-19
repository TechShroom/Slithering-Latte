package com.techshroom.slitheringlatte.helper.quickclassmaker;

import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import autovalue.shaded.com.google.common.common.collect.Lists;

import com.google.common.collect.ImmutableList;
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

    public static final ClassResolver RESOLVE_BY_IMPORTED =
            ClassResolveByPackages.create(IMPORTED_PACKAGES);
    public static final NameResolver RESOLVE_BY_IMPORTED_OR_GENEIRC =
            NameResolverImpl.create(RESOLVE_BY_IMPORTED);

    final class ClassResolveByPackages implements ClassResolver {

        private static final Map<Collection<String>, ClassResolveByPackages> cache =
                new HashMap<>();

        public static final ClassResolveByPackages create(
                Collection<String> packages) {
            return create(packages, null);
        }

        public static final ClassResolveByPackages create(
                Collection<String> packages, ClassResolver fallback) {
            return cache.computeIfAbsent(packages, pkgs -> {
                return new ClassResolveByPackages(pkgs, fallback);
            });
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
                for (String pkg : packages) {
                    try {
                        return Class.forName(pkg + "." + raw);
                    } catch (ClassNotFoundException supressed) {
                        exception.addSuppressed(supressed);
                    }
                }
                try {
                    return fallback.orElseThrow(() -> exception).resolve(raw);
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
            result = prime * result + fallback.hashCode();
            result = prime * result + packages.hashCode();
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
            return fallback.equals(other.fallback)
                    && packages.equals(other.packages);
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
            } catch (Exception fail) {
                if (raw.indexOf('.') != -1) {
                    // try for class
                    name = ClassName.bestGuess(raw);
                } else {
                    name = TypeVariableName.get(raw);
                }
            }
            return null;
        }

    }

    class Attribute implements JavaPoetClassMaker<QuickClass.Attribute> {

        @Override
        public TypeSpec makeTypeSpec(QuickClass.Attribute qclass) {
            TypeSpec.Builder spec = parseClassDefinition(qclass);
            MethodSpec base =
                    MethodSpec.methodBuilder(qclass.getName()).build();
            spec.addMethod(base
                    .toBuilder()
                    .returns(
                            RESOLVE_BY_IMPORTED_OR_GENEIRC.resolve(qclass
                                    .getValueType())).build());
            return spec.build();
        }

    }

    class Method implements JavaPoetClassMaker<QuickClass.Method> {

        @Override
        public TypeSpec makeTypeSpec(QuickClass.Method qclass) {
            TypeSpec.Builder spec = parseClassDefinition(qclass);
            return spec.build();
        }

    }

    class Mix implements JavaPoetClassMaker<QuickClass.Mix> {

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

    TypeSpec makeTypeSpec(QC qclass);

}
