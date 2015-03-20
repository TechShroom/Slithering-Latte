
package com.techshroom.slitheringlatte.codeobjects;

import java.util.Collection;
import java.util.function.Function;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_Language<Container extends CodeContainer> extends Language<Container> {

  private final String name;
  private final Class<Container> codeContainerClass;
  private final Function<Collection<String>, Container> constructorFunction;

  AutoValue_Language(
      String name,
      Class<Container> codeContainerClass,
      Function<Collection<String>, Container> constructorFunction) {
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
    if (codeContainerClass == null) {
      throw new NullPointerException("Null codeContainerClass");
    }
    this.codeContainerClass = codeContainerClass;
    if (constructorFunction == null) {
      throw new NullPointerException("Null constructorFunction");
    }
    this.constructorFunction = constructorFunction;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Class<Container> getCodeContainerClass() {
    return codeContainerClass;
  }

  @Override
  public Function<Collection<String>, Container> getConstructorFunction() {
    return constructorFunction;
  }

  @Override
  public String toString() {
    return "Language{"
        + "name=" + name + ", "
        + "codeContainerClass=" + codeContainerClass + ", "
        + "constructorFunction=" + constructorFunction
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Language) {
      Language<?> that = (Language<?>) o;
      return (this.name.equals(that.getName()))
           && (this.codeContainerClass.equals(that.getCodeContainerClass()))
           && (this.constructorFunction.equals(that.getConstructorFunction()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= name.hashCode();
    h *= 1000003;
    h ^= codeContainerClass.hashCode();
    h *= 1000003;
    h ^= constructorFunction.hashCode();
    return h;
  }

}
