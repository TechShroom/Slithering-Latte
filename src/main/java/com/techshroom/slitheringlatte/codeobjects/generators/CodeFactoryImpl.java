package com.techshroom.slitheringlatte.codeobjects.generators;

import java.util.Collection;

import com.google.common.collect.ImmutableList;
import com.techshroom.slitheringlatte.array.GenerateArray;
import com.techshroom.slitheringlatte.codeobjects.CodeContainer;
import com.techshroom.slitheringlatte.codeobjects.Language;

/**
 * Implementation of CodeFactory.
 * 
 * @author Kenzie Togami
 */
public class CodeFactoryImpl implements CodeFactory {
    @Override
    public CodeContainer wrap(String code, Language language) {
        return wrap(GenerateArray.ofLinesInString(code), language);
    }

    @Override
    public CodeContainer wrap(String[] code, Language language) {
        return wrap(ImmutableList.copyOf(code), language);
    }

    @Override
    public CodeContainer wrap(Collection<String> code, Language language) {
        switch (language) {
            case JAVA:
                return StringJavaCodeContainer.wrap(code);
            case PYTHON:
                return StringPythonCodeContainer.wrap(code);
            default:
                throw new UnsupportedOperationException("unhandled language "
                        + language);
        }
    }

}
