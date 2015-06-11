package com.techshroom.slitheringlatte.python.interfaces;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.techshroom.slitheringlatte.python.interfaces.collections.abcs.PythonSized;
import com.techshroom.slitheringlatte.python.interfaces.generated.PythonObject;

/**
 * Basic interface things for a Python object.
 * 
 * @author Kenzie Togami
 */
public interface BasicPythonObject extends PythonObject<PythonObject<?>> {

    @Override
    default boolean bool() {
        if (this instanceof PythonSized) {
            return ((PythonSized) this).length() != 0;
        }
        throw new UnsupportedOperationException("not convertable");
    }

    @Override
    default byte[] bytes() {
        throw new UnsupportedOperationException("not convertable");
    }

    @Override
    default int compareTo(PythonObject<?> o) {
        throw new UnsupportedOperationException("not comparable");
    }

    @Override
    default void del() {
    }

    @Override
    default void delattr(String name) {
    }

    @Override
    default Map<String, Object> dict() {
        return SharedInterfaceObjectSpace.get(this, "dict", HashMap::new);
    }

    @Override
    default void dict(Map<String, Object> dict) {
        SharedInterfaceObjectSpace.set(this, "dict", dict);
    }

    /**
     * Subclasses should probably use
     * {@link Iterables#concat(Iterable, Iterable)} with
     * {@link #__defaultAttributeListing()} to add more entries.
     */
    @Override
    default Iterable<String> dir() {
        return __defaultAttributeListing();
    }

    /**
     * Returns the default attribute listing for the class.
     * 
     * @return The default attribute listing for the class
     */
    default Iterable<String> __defaultAttributeListing() {
        return Arrays.stream(getClass().getMethods()).map(Method::getName)::iterator;
    }

    @Override
    default Object getattribute(String name) {
        return dict().get(name);
    }

    @Override
    default String repr() {
        return toString();
    }

    @Override
    default Object setattr(String name, Object value) {
        return dict().put(name, value);
    }

    @Override
    default List<String> slots() {
        return FluentIterable.from(dir()).toList();
    }
}
