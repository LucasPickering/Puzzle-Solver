package puzzlesolver;

import puzzlesolver.side.Side;

/**
 * A class to represent a 4-sided puzzle piece. No instance of this class should be used for
 * anything other than {@link #addSide} until all 4 sizes have been added, so that no side iss
 * {@code null}.
 */
public class Piece {

  public enum Direction {
    NORTH, EAST, SOUTH, WEST
  }

  /**
   * All sides must be non-null. Sides are ordered in the same way as {@link Direction}: NORTH,
   * EAST, SOUTH, WEST.
   */
  private final Side[] sides = new Side[4];

  /**
   * Adds the given side to this piece in the given direction. If this piece already has a side in
   * that direction, does nothing.
   *
   * @param side the side to be added
   * @param dir  the direction of the side in this piece
   * @return whether or not the piece was added
   */
  public boolean addSide(Side side, Direction dir) {
    if (sides[dir.ordinal()] == null) {
      sides[dir.ordinal()] = side;
      return true;
    }
    return false;
  }

  /**
   * Gets a clone of the side of this piece with the given direction.
   *
   * @param dir the direction of the piece to be retrieved
   * @return a clone of the side in the given direction
   */
  public Side getSide(Direction dir) {
    return sides[dir.ordinal()].copy();
  }
}
