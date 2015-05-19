package com.techshroom.slitheringlatte.helper.parsing;

import java.util.Optional;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class Generic {

    public static final Generic create(String name) {
        return create(name, null, ImmutableList.of());
    }

    public static final Generic create(String name, Relation relation,
            Iterable<String> relatedNames) {
        return new AutoValue_Generic(name, Optional.ofNullable(relation),
                ImmutableList.copyOf(relatedNames));
    }

    Generic() {
    }

    public abstract String getName();

    public abstract Optional<Relation> getRelation();

    public abstract ImmutableList<String> getRelatedNames();

}
