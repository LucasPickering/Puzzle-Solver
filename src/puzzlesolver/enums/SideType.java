package puzzlesolver.enums;

import java.util.Objects;

public enum SideType {
  IN, OUT, FLAT;

  /**
   * Check if this {@link SideType} and the given {@link SideType} are capable of fitting with each
   * other.
   * @param s the side time to check compatibility with
   *
   * @return 0 if the sides are compatible, else another integer.
   */
  public int compatible(SideType s) {
    Objects.requireNonNull(s);

    if (this == FLAT || s == FLAT) {
      return (this.equals(s)) ? -1 : this.compareTo(s);
    }
    return (this.equals(s)) ? 1 : 0;
  }
}
