
package com.techshroom.slitheringlatte.codeobjects.generators;

import java.util.Collection;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_StringPythonCodeContainer extends StringPythonCodeContainer {

  private final Collection<String> lines;

  AutoValue_StringPythonCodeContainer(
      Collection<String> lines) {
    if (lines == null) {
      throw new NullPointerException("Null lines");
    }
    this.lines = lines;
  }

  @Override
  public Collection<String> getLines() {
    return lines;
  }

  @Override
  public String toString() {
    return "StringPythonCodeContainer{"
        + "lines=" + lines
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof StringPythonCodeContainer) {
      StringPythonCodeContainer that = (StringPythonCodeContainer) o;
      return (this.lines.equals(that.getLines()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= lines.hashCode();
    return h;
  }

}
