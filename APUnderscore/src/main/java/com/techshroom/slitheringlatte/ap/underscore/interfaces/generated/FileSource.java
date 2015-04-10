package com.techshroom.slitheringlatte.ap.underscore.interfaces.generated;

import com.techshroom.slitheringlatte.ap.underscore.annotations.InterfaceType;
import com.techshroom.slitheringlatte.ap.underscore.annotations.PythonName;
import com.techshroom.slitheringlatte.ap.underscore.interfaces.DunderAttribute;

@InterfaceType(InterfaceType.Value.ATTRIBUTE)
@PythonName("__file__")
public interface FileSource extends DunderAttribute {
    String file();
}
