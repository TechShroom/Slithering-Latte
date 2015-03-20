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
    public <CType extends CodeContainer> CType wrap(String code,
            Language<CType> language) {
        return wrap(GenerateArray.ofLinesInString(code), language);
    }

    @Override
    public <CType extends CodeContainer> CType wrap(String[] code,
            Language<CType> language) {
        return wrap(ImmutableList.copyOf(code), language);
    }

    @Override
    public <CType extends CodeContainer> CType wrap(Collection<String> code,
            Language<CType> language) {
        CType container = language.getConstructorFunction().apply(code);
        if (container == null) {
            throw new UnsupportedOperationException("Uknown language "
                    + language.getName() + ".");
        }
        return container;
    }

}
