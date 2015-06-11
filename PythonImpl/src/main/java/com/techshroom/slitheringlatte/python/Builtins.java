package com.techshroom.slitheringlatte.python;

import com.techshroom.slitheringlatte.python.interfaces.generated.Representable;

public final class Builtins {

    public static enum NotImplementedType implements Representable {

        INSTANCE;

        @Override
        public String repr() {
            return "NotImplemented";
        }

        @Override
        public String toString() {
            return repr();
        }

    }

    public static enum NoneType implements Representable {

        INSTANCE;

        @Override
        public String repr() {
            return "None";
        }

        @Override
        public String toString() {
            return repr();
        }

    }

    public static final NotImplementedType NotImplemented =
            NotImplementedType.INSTANCE;
    public static final NoneType None = NoneType.INSTANCE;

    public static int hash(Object o) {
        return 0;
    }

    private Builtins() {
    }

}
