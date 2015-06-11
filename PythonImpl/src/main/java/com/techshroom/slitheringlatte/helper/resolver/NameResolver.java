package com.techshroom.slitheringlatte.helper.resolver;

import com.squareup.javapoet.TypeName;

public interface NameResolver {

    /**
     * Resolves a raw string to a valid TypeName.
     * 
     * @param raw
     *            - The raw string
     * @return resolved type name
     */
    TypeName resolve(String raw);

}
