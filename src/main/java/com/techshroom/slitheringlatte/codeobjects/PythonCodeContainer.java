package com.techshroom.slitheringlatte.codeobjects;

/**
 * A CodeContainer for Python.
 * 
 * @author Kenzie Togami
 */
public interface PythonCodeContainer extends CodeContainer {
    @Override
    default public Language language() {
        return Language.PYTHON;
    }
}
