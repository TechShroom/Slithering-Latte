package com.techshroom.slitheringlatte.ap.underscore.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta-annotation indicating that the marked annotation is representing an
 * underscore attribute.
 * 
 * @author Kenzie Togami
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface UnderscoreAnnotation {

    /**
     * The original python name for this UnderscoreAnnotation. Used to create
     * more readable annotations. The default value indicates that the current
     * name should be converted to lower-case and have two underscores added on
     * each side. Fox example, {@code @Doc} would become {@code __doc__}.
     */
    String pythonName() default "";

}
