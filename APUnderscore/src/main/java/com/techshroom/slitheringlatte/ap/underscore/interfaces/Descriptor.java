package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

public interface Descriptor<T, V> {

    @PythonName("__get__")
    Object get(T instance, Class<T> owner);

    @PythonName("__set__")
    void set(T instance, V value);

    @PythonName("__delete__")
    void delete(T instance);

}
