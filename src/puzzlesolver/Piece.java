package puzzlesolver;

import puzzlesolver.side.Side;

/**
 * A class to represent a 4-sided puzzle piece.
 */
public class Piece {

  public enum Direction {
    NORTH, EAST, SOUTH, WEST
  }

  public static class Builder {

    private final Side[] sides = new Side[4];

    /**
     * Sets this piece's side in the given direction to the given side.
     *
     * @param side the side to be added
     * @param dir  the direction of the side in this piece
     */
    public Builder setSide(Side side, Direction dir) {
      sides[dir.ordinal()] = side;
      return this;
    }

    public Piece build() {
      for (Side side : sides) {
        if (side == null) {
          throw new IllegalStateException("Not all sides are initialized yet");
        }
      }
      return new Piece(sides);
    }
  }

  /**
   * All sides must be non-null. Sides are ordered in the same way as {@link Direction}: NORTH,
   * EAST, SOUTH, WEST.
   */
  private final Side[] sides;

  /**
   * Constructs a new Piece with the given 4 sides.
   * @param sides array of sides, length MUST be 4
   */
  private Piece(Side[] sides) {
    this.sides = sides;
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
