package com.techshroom.slitheringlatte.helper;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class HelperConstants {

    // it looks pretty...
    public static final class src {

        public static final class generated {

            public static final Path path = Paths.get("src", "generated");

            public static final class java {

                public static final Path path = src.generated.path
                        .resolve("java");

            }

            public static final class resources {

                public static final Path path = src.generated.path
                        .resolve("resources");

            }

        }

        public static final class main {

            public static final Path path = Paths.get("src", "main");

            public static final class java {

                public static final Path path = src.main.path.resolve("java");

            }

            public static final class resources {

                public static final Path path = src.main.path
                        .resolve("resources");

            }

        }

        public static final class test {

            public static final Path path = Paths.get("src", "test");

            public static final class java {

                public static final Path path = src.test.path.resolve("java");

            }

            public static final class resources {

                public static final Path path = src.test.path
                        .resolve("resources");

            }

        }

    }

    private HelperConstants() {
    }

}