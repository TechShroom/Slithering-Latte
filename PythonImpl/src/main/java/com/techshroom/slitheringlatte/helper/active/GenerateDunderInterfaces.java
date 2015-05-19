package com.techshroom.slitheringlatte.helper.active;

import com.techshroom.slitheringlatte.helper.quickclassmaker.QuickClass;

public class GenerateDunderInterfaces {

    public static void main(String[] args) {
        QuickClass deletable =
                QuickClass.Method.builder().originalPythonName("__del__")
                        .classDefinition("Deleteable").methodName("del")
                        .returnType("void").build();
        QuickClass representable =
                QuickClass.Method.builder().originalPythonName("__repr__")
                        .classDefinition("Representable").methodName("repr")
                        .returnType("String").build();
    }

}
