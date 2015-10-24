package puzzlesolver.enums;

public enum SideType {
  IN, OUT, FLAT;

  public boolean isFlat() {
    return this == FLAT;
  }
}
