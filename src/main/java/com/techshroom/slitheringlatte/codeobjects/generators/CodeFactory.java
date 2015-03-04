package com.techshroom.slitheringlatte.codeobjects.generators;

import java.util.Collection;

import com.techshroom.slitheringlatte.codeobjects.CodeContainer;
import com.techshroom.slitheringlatte.codeobjects.Language;

/**
 * Code container factory.
 * 
 * @author Kenzie Togami
 */
public interface CodeFactory {
    /**
     * Creates a new code container wrapping the given values.
     * 
     * @param code
     *            - the code
     * @param language
     *            - the language of the code
     * @return a new code container wrapping the given values.
     */
    <CType extends CodeContainer> CType wrap(String code,
            Language<CType> language);

    /**
     * Creates a new code container wrapping the given values.
     * 
     * @param code
     *            - the code. Strings containing newlines may or may not be
     *            split.
     * @param language
     *            - the language of the code
     * @return a new code container wrapping the given values.
     */
    <CType extends CodeContainer> CType wrap(String[] code,
            Language<CType> language);

    /**
     * Creates a new code container wrapping the given values.
     * 
     * @param code
     *            - the code. Strings containing newlines may or may not be
     *            split.
     * @param language
     *            - the language of the code
     * @return a new code container wrapping the given values.
     */
    <CType extends CodeContainer> CType wrap(Collection<String> code,
            Language<CType> language);
}
