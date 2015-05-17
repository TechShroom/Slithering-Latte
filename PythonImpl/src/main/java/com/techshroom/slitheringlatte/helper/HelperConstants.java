package com.techshroom.slitheringlatte.helper;

import java.nio.file.Path;
import java.nio.file.Paths;

final class HelperConstants {
	// it looks pretty...
	static final class src {
		static final class main {
			static final Path path = Paths.get("src", "main");

			static final class java {
				static final Path path = src.main.path.resolve("java");
			}

			static final class resources {
				static final Path path = src.main.path.resolve("resources");
			}
		}
	}

	private HelperConstants() {
	}
}
