package com.techshroom.slitheringlatte.python.interfaces.generated;

import com.techshroom.slitheringlatte.python.annotations.InterfaceType;
import com.techshroom.slitheringlatte.python.annotations.KeywordArgs;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.interfaces.DunderAttribute;
import java.util.List;
import java.util.Map;

@InterfaceType(InterfaceType.Value.METHOD)
@PythonName("__prepare__")
public interface NamespacePreparer extends DunderAttribute {
    default Map<String, Object> prepare(String name, List<String> bases,
            @KeywordArgs Map<String, Object> kwargs) {
        return new java.util.HashMap<>();
    }
}
