package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;

@PythonName("id")
public interface Identifiable extends DunderAttribute {
    @Override
    int hashCode();
}
