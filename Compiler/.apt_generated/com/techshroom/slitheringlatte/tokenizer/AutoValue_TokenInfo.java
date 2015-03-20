
package com.techshroom.slitheringlatte.tokenizer;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_TokenInfo extends TokenInfo {

  private final Token type;
  private final String string;
  private final LinePosition start;
  private final LinePosition end;
  private final int line;

  AutoValue_TokenInfo(
      Token type,
      String string,
      LinePosition start,
      LinePosition end,
      int line) {
    if (type == null) {
      throw new NullPointerException("Null type");
    }
    this.type = type;
    if (string == null) {
      throw new NullPointerException("Null string");
    }
    this.string = string;
    if (start == null) {
      throw new NullPointerException("Null start");
    }
    this.start = start;
    if (end == null) {
      throw new NullPointerException("Null end");
    }
    this.end = end;
    this.line = line;
  }

  @Override
  public Token type() {
    return type;
  }

  @Override
  public String string() {
    return string;
  }

  @Override
  public LinePosition start() {
    return start;
  }

  @Override
  public LinePosition end() {
    return end;
  }

  @Override
  public int line() {
    return line;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof TokenInfo) {
      TokenInfo that = (TokenInfo) o;
      return (this.type.equals(that.type()))
           && (this.string.equals(that.string()))
           && (this.start.equals(that.start()))
           && (this.end.equals(that.end()))
           && (this.line == that.line());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= type.hashCode();
    h *= 1000003;
    h ^= string.hashCode();
    h *= 1000003;
    h ^= start.hashCode();
    h *= 1000003;
    h ^= end.hashCode();
    h *= 1000003;
    h ^= line;
    return h;
  }

}
