package puzzlesolver;

import puzzlesolver.side.Side;

/**
 * A class to represent a 4-sided puzzle piece.
 */
public class Piece {

  enum Direction {
    NORTH, EAST, SOUTH, WEST
  }

  /**
   * All sides must be non-null. Sides are ordered in the same way as {@link Direction}: NORTH,
   * EAST, SOUTH, WEST.
   */
  private final Side[] sides = new Side[4];

}
