package com.techshroom.slitheringlatte.helper.parsing;

import java.util.Locale;

public enum Relation {

    SUPER, EXTENDS;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH);
    };

}
