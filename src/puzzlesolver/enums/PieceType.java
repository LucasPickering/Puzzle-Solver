package puzzlesolver.enums;

import puzzlesolver.constants.Constants;
import puzzlesolver.Piece;
import puzzlesolver.Side;

public enum PieceType {

  CORNER(null, null, 2), EDGE(null, null, 1), ALL_IN(4, 0, 0), ALL_OUT(0, 4, 0),
  THREE_IN(3, 1, 0), THREE_OUT(1, 3, 0), OPPOSITES(2, 2, 0), ADJACENTS(2, 2, 0);

  private final Integer[] sideTypes;

  /**
   * Constructs a new PieceType with the given amount of each side type, in the order defined in
   * {@link SideType}
   *
   * @param sideTypes the amount of each side type, with the order and size of {@link SideType}; can
   *                  have null if the amount of a specific side type is undefined, e.g. in corners
   */
  PieceType(Integer... sideTypes) {
    if (sideTypes.length != 3) {
      throw new IllegalArgumentException("sideTypes must be length == 3");
    }

    this.sideTypes = sideTypes;
    int definedSides = 0;
    for (Integer i : sideTypes) {
      definedSides += i == null ? 0 : i;
    }

    if (definedSides > 4) {
      throw new IllegalArgumentException("definedSides must be <= 4");
    }
  }

  /**
   * Can the given piece be of this type?
   *
   * @param p the piece to be checked
   * @return true if the piece can be of this type, false if it cannot
   */
  public boolean canBeType(Piece p) {
    SideType[] sides = new SideType[Constants.NUM_SIDES];
    int[] sideTypeAmounts = new int[SideType.values().length];
    int remainingSides = Constants.NUM_SIDES;
    for (Direction dir : Direction.values()) {
      final Side side = p.getSide(dir);
      if (side != null) {
        SideType st = side.getSideType();
        sides[dir.ordinal()] = st;
        ++sideTypeAmounts[side.getSideType().ordinal()];
        --remainingSides;
      }
    }

    for (int i = 0; i < sideTypeAmounts.length; i++) {
      if (sideTypes[i] != null) {
        int required = sideTypes[i] - sideTypeAmounts[i];
        if (required >= 0) {
          if (remainingSides < required) {
            // Has too many of the given side type
            // or has too few of the given side type and not enough spare sides to fill them
            return false;
          } else {
            // Take the sides away from the remaining sides able to be filled
            remainingSides -= required;
          }
        } else {
          return false;
        }
      }
    }

    switch (this) {
      case OPPOSITES:
        return sides[0] == sides[2]
               && sides[1] == sides[3];

      case ADJACENTS:
        return (sides[0] == sides[1] && sides[2] == sides[3])
               || (sides[0] == sides[3] && sides[1] == sides[2]);

      default:
        return true;
    }
  }
}
