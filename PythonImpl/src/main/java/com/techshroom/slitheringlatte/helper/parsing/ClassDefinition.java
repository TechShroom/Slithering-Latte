package com.techshroom.slitheringlatte.helper.parsing;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.stream.Collectors;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class ClassDefinition {

    public static final ClassDefinition create(String name,
            Iterable<Generic> generics) {
        checkArgument(Parsing.VALID_JAVA_CLASS.matcher(name).matches(),
                "%s is not a valid class name", name);
        return new AutoValue_ClassDefinition(name,
                ImmutableList.copyOf(generics));
    }

    ClassDefinition() {
    }

    public abstract String getName();

    public abstract ImmutableList<Generic> getGenerics();

    public String toRawDefinition() {
        return getName()
                + (getGenerics().isEmpty() ? "" : getGenerics().stream()
                        .map(Generic::toRawGeneric)
                        .collect(Collectors.joining(", ", "<", ">")));
    }

}
