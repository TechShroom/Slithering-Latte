package com.techshroom.slitheringlatte.helper.resolver;


public interface ClassResolver {
	/**
	 * Resolves a string to a class.
	 * @param raw - The raw string
	 * @return The resolved class
	 */
	Class<?> resolve(String raw);
}
