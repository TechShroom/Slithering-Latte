package com.techshroom.slitheringlatte.codeobjects;

import java.util.Collection;
import java.util.function.Function;

import com.google.auto.value.AutoValue;
import com.techshroom.slitheringlatte.codeobjects.generators.StringJavaCodeContainer;
import com.techshroom.slitheringlatte.codeobjects.generators.StringPythonCodeContainer;

/**
 * Different language enums for the compiler.
 * 
 * @author Kenzie Togami
 * @param <Container>
 *            - The type of the code container that this language uses
 */
@AutoValue
public abstract class Language<Container extends CodeContainer> {
    /**
     * Creates a new language, with the code container type set to the most
     * broad one.
     * 
     * @param name
     *            - The name of the language
     * @return The Language instance created
     */
    public static Language<CodeContainer> newBasicLanguage(String name) {
        return newLanguage(name, CodeContainer.class, x -> null);
    }

    /**
     * Creates a new language, with the code container type set to the given
     * one.
     * 
     * @param name
     *            - The name of the language
     * @param containerType
     *            - The class of the code container
     * @param constructorFunction
     *            - The function that can be used to create new code containers
     * @return The Language instance created
     */
    public static <CType extends CodeContainer> Language<CType> newLanguage(
            String name, Class<CType> containerType,
            Function<Collection<String>, CType> constructorFunction) {
        return new AutoValue_Language<>(name, containerType,
                constructorFunction);
    }

    /**
     * Represents the Python language.
     */
    public static final Language<PythonCodeContainer> PYTHON =
            newLanguage("Python", PythonCodeContainer.class,
                        StringPythonCodeContainer::wrap);
    /**
     * Represents the Java language.
     */
    public static final Language<JavaCodeContainer> JAVA =
            newLanguage("Java", JavaCodeContainer.class,
                        StringJavaCodeContainer::wrap);

    /**
     * Returns the name of this Language.
     * 
     * @return The name of this Language
     */
    public abstract String getName();

    /**
     * Returns the class of the code container.
     * 
     * @return The class of the code container
     */
    public abstract Class<Container> getCodeContainerClass();

    /**
     * Returns a function that can be used to make instances of the code
     * container type. The given function may return {@code null}, indicating
     * that there is no implementation of wrapping for this language.
     * 
     * <p>
     * Note: This is only meant for use by CodeFactory implementations.
     * </p>
     * 
     * @return A function that can be used to make instances of the code
     *         container type
     */
    public abstract Function<Collection<String>, Container>
            getConstructorFunction();
}
