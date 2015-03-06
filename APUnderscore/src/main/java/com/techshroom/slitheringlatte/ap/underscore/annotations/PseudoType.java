package com.techshroom.slitheringlatte.ap.underscore.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta-annotation indicating that the marked annotation is representing a
 * runtime type that contains multiple {@link UnderscoreAttribute
 * UnderscoreAttributes}.
 * 
 * @author Kenzie Togami
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface PseudoType {
}
