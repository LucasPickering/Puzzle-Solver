package puzzlesolver;

import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;
import puzzlesolver.side.Side;

/**
 * A class to represent a 4-sided puzzle piece.
 *
 * This piece has a type that MUST be determined during solution and CANNOT be provided to it or
 * determined during generation.
 */
public class Piece {

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
  private PieceType pieceType;

  /**
   * Constructs a new Piece with the given 4 sides.
   *
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

  /**
   * Gets the {@link PieceType} type of this piece. If is currently unknown, calculates the type and
   * saves it for future use.
   *
   * @return the type of this piece
   */
  public PieceType getPieceType() {
    if (pieceType == null) {
      pieceType = findPieceType();
    }
    return pieceType;
  }

  private PieceType findPieceType() {
    int flatSides = 0;
    int inSides = 0;
    for (Side side : sides) {
      switch (side.getSideType()) {
        case FLAT:
          flatSides++;
          break;
        case IN:
          inSides++;
          break;
      }
    }
    if (flatSides > 1) {
      return PieceType.CORNER;
    } else if (flatSides == 1) {
      return PieceType.EDGE;
    }

    // Known: flatSides == 0; inSides + outSides == 4
    switch (inSides) {
      case 0:
        return PieceType.ALL_OUT;
      case 1:
        return PieceType.THREE_OUT;
      case 2:
        return (sides[0].getSideType() ==
            sides[2].getSideType()) ? PieceType.OPPOSITES : PieceType.ADJACENTS;
      case 3:
        return PieceType.THREE_IN;
      case 4:
        return PieceType.ALL_IN;
      default:
        throw new IllegalStateException("inSides > 4, that's impossible!");
    }
  }
}
