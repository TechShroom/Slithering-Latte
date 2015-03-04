package com.techshroom.slitheringlatte.codeobjects;

/**
 * A CodeContainer for Java.
 * 
 * @author Kenzie Togami
 */
public interface JavaCodeContainer extends CodeContainer {
    @Override
    default public Language<JavaCodeContainer> language() {
        return Language.JAVA;
    }
}
