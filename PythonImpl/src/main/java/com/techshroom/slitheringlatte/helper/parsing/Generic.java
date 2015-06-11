package com.techshroom.slitheringlatte.helper.parsing;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Iterator;
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
        ImmutableList<String> list = ImmutableList.copyOf(relatedNames);
        checkArgument((relation == null) == list.isEmpty(),
                "A relation was %spresent, but there was%s relation names",
                relation == null ? "not " : "", list.isEmpty() ? " not any"
                        : "");
        return new AutoValue_Generic(name, Optional.ofNullable(relation), list);
    }

    Generic() {
    }

    public abstract String getName();

    public abstract Optional<Relation> getRelation();

    public abstract ImmutableList<String> getRelatedNames();

    public String toRawGeneric() {
        StringBuilder b = new StringBuilder(getName());
        if (getRelation().isPresent()) {
            b.append(' ').append(getRelation().get()).append(' ');
            for (Iterator<String> iterator = getRelatedNames().iterator(); iterator
                    .hasNext();) {
                String name = iterator.next();
                b.append(name);
                if (iterator.hasNext()) {
                    b.append(", ");
                }
            }
        }
        return b.toString();
    }

}
