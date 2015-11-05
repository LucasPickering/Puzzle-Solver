package puzzlesolver.enums;

import puzzlesolver.Constants;

public enum Direction {

  NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);

  public final int x;
  public final int y;

  /**
   * Constructs a new direction which is in the given x and y direction. If x is 0, then y can be -1
   * or 1. If y is 0, x can be -1 or 1. No other values are permitted.
   *
   * @param x x-offset of this direction; [-1, 1]
   * @param y y-offset of this direction; [-1, 1]
   */
  Direction(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the direction opposite of this this one, e.g. EAST.opposite() returns WEST.
   *
   * @return the opposite direction
   */
  public Direction opposite() {
    return values()[(ordinal() + Constants.NUM_SIDES / 2) % Constants.NUM_SIDES];
  }
}
