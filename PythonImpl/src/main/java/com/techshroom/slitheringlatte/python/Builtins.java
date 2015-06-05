package com.techshroom.slitheringlatte.python;

import com.techshroom.slitheringlatte.python.interfaces.generated.Representable;

public final class Builtins {

    public static enum NotImplementedType implements Representable {

        INSTANCE;

        @Override
        public String repr() {
            return "NotImplemented";
        }

    }

    public static final NotImplementedType NotImplemented =
            NotImplementedType.INSTANCE;

    public static int hash(Object o) {
        return 0;
    }

    private Builtins() {
    }

}
