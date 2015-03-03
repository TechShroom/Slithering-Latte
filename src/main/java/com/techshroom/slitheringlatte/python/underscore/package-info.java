/**
 * Support for Python's {@code __underscore__} functions in Java. All interfaces are named with
 * {@code underscore.toCamelCase() + "Supported"} and contain one method,
 * named {@code underscore}.<br>
 * <br>
 * There is one exception, and that is the {@code __str__} function.
 * It maps directly to {@link #toString()}, unless the StrSupported 
 * interface is used. Because of this difference, it is easiest to use
 * {@link com.techshroom.slitheringlatte.python.underscore.StrSupported#str(Object) 
 * StrSupported.str(Object)}.
 */
package com.techshroom.slitheringlatte.python.underscore;

