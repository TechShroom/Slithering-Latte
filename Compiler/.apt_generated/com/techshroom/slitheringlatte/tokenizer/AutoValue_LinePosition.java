
package com.techshroom.slitheringlatte.tokenizer;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_LinePosition extends LinePosition {

  private final int line;
  private final int position;

  AutoValue_LinePosition(
      int line,
      int position) {
    this.line = line;
    this.position = position;
  }

  @Override
  public int getLine() {
    return line;
  }

  @Override
  public int getPosition() {
    return position;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof LinePosition) {
      LinePosition that = (LinePosition) o;
      return (this.line == that.getLine())
           && (this.position == that.getPosition());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= line;
    h *= 1000003;
    h ^= position;
    return h;
  }

}
