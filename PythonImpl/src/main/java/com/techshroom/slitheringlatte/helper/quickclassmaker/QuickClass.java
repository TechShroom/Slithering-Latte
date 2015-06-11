package com.techshroom.slitheringlatte.helper.quickclassmaker;

import java.util.Optional;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.CodeBlock;
import com.techshroom.slitheringlatte.python.annotations.MethodType;

public interface QuickClass {

    interface Builder<T extends QuickClass, B extends Builder<T, B>> {

        T build();

        B classDefinition(String def);

        String getClassDefinition();

    }

    @AutoValue
    static abstract class Attribute implements QuickClass {

        public static final Builder builder() {
            return new AutoValue_QuickClass_Attribute.Builder().writable(false)
                    .originalPythonName(Optional.empty());
        }

        @AutoValue.Builder
        public static abstract class Builder implements
                QuickClass.Builder<Attribute, Builder> {

            public abstract Builder packageName(String pkg);

            public Builder pythonNameAndMethodName(String name) {
                String mName = name;
                while (mName.startsWith("_")) {
                    mName = mName.substring(1);
                }
                while (mName.endsWith("_")) {
                    mName = mName.substring(0, mName.length() - 1);
                }
                return originalPythonName(name).name(mName);
            }

            public Builder originalPythonName(String name) {
                return originalPythonName(Optional.ofNullable(name));
            }

            abstract Builder originalPythonName(Optional<String> name);

            @Override
            public abstract Builder classDefinition(String def);

            public abstract Builder writable(boolean writable);

            public abstract Builder name(String name);

            public abstract Builder valueType(String name);

            @Override
            public abstract Attribute build();

        }

        Attribute() {
        }

        @Override
        public MethodType.Value getType() {
            return MethodType.Value.ATTRIBUTE;
        }

        public abstract boolean isWritable();

        public abstract String getName();

        public abstract String getValueType();

        public Builder toBuilder() {
            return new AutoValue_QuickClass_Attribute.Builder(this);
        }

    }

    @AutoValue
    static abstract class Method implements QuickClass {

        public static final Builder builder() {
            return new AutoValue_QuickClass_Method.Builder().defaultCode(
                    Optional.empty()).originalPythonName(Optional.empty());
        }

        @AutoValue.Builder
        public static abstract class Builder implements
                QuickClass.Builder<Method, Builder> {

            public abstract Builder packageName(String pkg);

            public Builder pythonNameAndMethodName(String name) {
                String mName = name;
                while (mName.startsWith("_")) {
                    mName = mName.substring(1);
                }
                while (mName.endsWith("_")) {
                    mName = mName.substring(0, mName.length() - 1);
                }
                return originalPythonName(name).name(mName);
            }

            public Builder originalPythonName(String name) {
                return originalPythonName(Optional.ofNullable(name));
            }

            abstract Builder originalPythonName(Optional<String> name);

            @Override
            public abstract Builder classDefinition(String def);

            public abstract Builder name(String name);

            public Builder addParameter(String parameter) {
                parametersBuilder().add(parameter);
                return this;
            }

            public Builder addParameters(String... parameter) {
                parametersBuilder().add(parameter);
                return this;
            }

            abstract ImmutableList.Builder<String> parametersBuilder();

            public abstract Builder returnType(String type);

            public Builder defaultCode(CodeBlock code) {
                return defaultCode(Optional.ofNullable(code));
            }

            abstract Builder defaultCode(Optional<CodeBlock> code);

            @Override
            public abstract Method build();

        }

        Method() {
        }

        @Override
        public MethodType.Value getType() {
            return MethodType.Value.METHOD;
        }

        public abstract String getName();

        public abstract ImmutableList<String> getParameters();

        public abstract String getReturnType();

        public abstract Optional<CodeBlock> getDefaultCode();

        public Builder toBuilder() {
            return new AutoValue_QuickClass_Method.Builder(this);
        }

    }

    @AutoValue
    static abstract class Mix implements QuickClass {

        public static final Builder builder() {
            return new AutoValue_QuickClass_Mix.Builder()
                    .originalPythonName(Optional.empty());
        }

        @AutoValue.Builder
        public static abstract class Builder implements
                QuickClass.Builder<Mix, Builder> {

            public abstract Builder packageName(String pkg);

            public Builder originalPythonName(String name) {
                return originalPythonName(Optional.ofNullable(name));
            }

            abstract Builder originalPythonName(Optional<String> name);

            @Override
            public abstract Builder classDefinition(String def);

            public Builder addSuperInterface(String name) {
                superInterfacesBuilder().add(name);
                return this;
            }

            public Builder addSuperInterfaces(String first, String second,
                    String... rest) {
                String[] nArray = new String[rest.length + 2];
                nArray[0] = first;
                nArray[1] = second;
                System.arraycopy(rest, 0, nArray, 2, rest.length);
                superInterfacesBuilder().add(nArray);
                return this;
            }

            abstract ImmutableList.Builder<String> superInterfacesBuilder();

            @Override
            public abstract Mix build();

        }

        Mix() {
        }

        @Override
        public MethodType.Value getType() {
            return MethodType.Value.MIX;
        }

        public abstract ImmutableList<String> getSuperInterfaces();

        public Builder toBuilder() {
            return new AutoValue_QuickClass_Mix.Builder(this);
        }

    }

    String getPackageName();

    MethodType.Value getType();

    String getClassDefinition();

    Optional<String> getOriginalPythonName();

}
