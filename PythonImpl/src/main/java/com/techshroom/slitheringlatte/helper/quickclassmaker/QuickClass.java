package com.techshroom.slitheringlatte.helper.quickclassmaker;

import java.util.Optional;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.techshroom.slitheringlatte.python.annotations.InterfaceType;

public interface QuickClass {

    @AutoValue
    static abstract class Attribute implements QuickClass {

        public static final Builder builder() {
            return new AutoValue_QuickClass_Attribute.Builder();
        }

        @AutoValue.Builder
        public static abstract class Builder {

            public abstract Builder packageName(String pkg);

            public Builder originalPythonName(String name) {
                return originalPythonName(Optional.ofNullable(name));
            }

            abstract Builder originalPythonName(Optional<String> name);

            public abstract Builder classDefinition(String def);

            public abstract Builder writable(boolean writable);

            public abstract Builder name(String name);

            public abstract Builder valueType(String name);

            public abstract Attribute build();

        }

        Attribute() {
        }

        @Override
        public InterfaceType.Value getType() {
            return InterfaceType.Value.ATTRIBUTE;
        }

        public abstract boolean isWritable();

        public abstract String getName();

        public abstract String getValueType();
    }

    @AutoValue
    static abstract class Method implements QuickClass {

        public static final Builder builder() {
            return new AutoValue_QuickClass_Method.Builder();
        }

        @AutoValue.Builder
        public static abstract class Builder {

            public abstract Builder packageName(String pkg);

            public Builder originalPythonName(String name) {
                return originalPythonName(Optional.ofNullable(name));
            }

            abstract Builder originalPythonName(Optional<String> name);

            public abstract Builder classDefinition(String def);

            public abstract Builder methodName(String name);

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

            public abstract Method build();
        }

        Method() {
        }

        @Override
        public InterfaceType.Value getType() {
            return InterfaceType.Value.METHOD;
        }

        public abstract String getMethodName();

        public abstract ImmutableList<String> getParameters();

        public abstract String getReturnType();
    }

    @AutoValue
    static abstract class Mix implements QuickClass {

        public static final Builder builder() {
            return new AutoValue_QuickClass_Mix.Builder();
        }

        @AutoValue.Builder
        public static abstract class Builder {

            public abstract Builder packageName(String pkg);

            public Builder originalPythonName(String name) {
                return originalPythonName(Optional.ofNullable(name));
            }

            abstract Builder originalPythonName(Optional<String> name);

            public abstract Builder classDefinition(String def);

            public Builder addSuperInterface(String name) {
                superInterfacesBuilder().add(name);
                return this;
            }

            public Builder addSuperInterfaces(String... names) {
                superInterfacesBuilder().add(names);
                return this;
            }

            abstract ImmutableList.Builder<String> superInterfacesBuilder();

            public abstract Mix build();
        }

        Mix() {
        }

        @Override
        public InterfaceType.Value getType() {
            return InterfaceType.Value.MIX;
        }

        public abstract ImmutableList<String> getSuperInterfaces();
    }

    String getPackageName();

    InterfaceType.Value getType();

    String getClassDefinition();

    Optional<String> getOriginalPythonName();

}
