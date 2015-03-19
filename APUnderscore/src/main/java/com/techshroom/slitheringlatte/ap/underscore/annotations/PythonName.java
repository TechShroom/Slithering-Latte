package com.techshroom.slitheringlatte.ap.underscore.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classes annotated with this annotation have a different name in Python.
 * 
 * @author Kenzie Togami
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PythonName {

    /**
     * The Python name.
     */
    String value() default "";

}
