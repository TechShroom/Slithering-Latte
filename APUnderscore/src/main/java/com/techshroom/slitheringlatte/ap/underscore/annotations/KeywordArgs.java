package com.techshroom.slitheringlatte.ap.underscore.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Parameters annotated with this annotation are a map of the variable keyword
 * arguments passed to the function.
 * 
 * @author Kenzie Togami
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface KeywordArgs {

}
