package com.techshroom.slitheringlatte.logging;

import java.util.Set;
import java.util.stream.Stream;

import autovalue.shaded.com.google.common.common.collect.Sets;

public enum DefaultLevel implements Level {
    DEVNULL("/dev/null", false), FATAL("FATAL", true), ERROR("ERROR", true),
    WARN("WARNING", true), INFO("INFO", true), DEBUG("DEBUG", true), TRACE(
            "TRACE", true), ALL("ALL", false);

    public static final Set<DefaultLevel> PRINTING_LEVELS = Sets
            .immutableEnumSet(Stream.of(values()).filter(
                    DefaultLevel::isPrintableLevel)::iterator);

    private final String repr;
    private final boolean printableLevel;

    private DefaultLevel(String repr, boolean printable) {
        this.repr = repr;
        this.printableLevel = printable;
    }

    @Override
    public String getLogRepresentation() {
        return this.repr;
    }

    public boolean isPrintableLevel() {
        return this.printableLevel;
    }

}
