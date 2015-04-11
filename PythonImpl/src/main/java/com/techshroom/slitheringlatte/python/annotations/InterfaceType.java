package com.techshroom.slitheringlatte.python.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Targets annotated with this annotation are of the given "type" in Python.
 * {@link Value#MIX} represents a mixture of the others, and is usually linked
 * to a certain group of objects in Python.
 * 
 * @author Kenzie Togami
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InterfaceType {

    public enum Value {

        ATTRIBUTE, METHOD, MIX;

    }

    /**
     * The interface type.
     */
    Value value();

}
