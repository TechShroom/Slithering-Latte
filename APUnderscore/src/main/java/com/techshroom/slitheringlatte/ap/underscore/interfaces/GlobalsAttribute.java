package com.techshroom.slitheringlatte.ap.underscore.interfaces;

import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import java.util.Map;

@PythonName("__global__")
public interface GlobalsAttribute<K, V> extends DunderAttribute {
    Map<K, V> global();
}
