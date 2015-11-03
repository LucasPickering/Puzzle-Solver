package puzzlesolver.enums;

import puzzlesolver.Piece;
import puzzlesolver.Side;

public enum PieceType {

  CORNER(null, null, 2), EDGE(null, null, 1), ALL_IN(4, 0, 0), ALL_OUT(0, 4, 0),
  THREE_IN(3, 1, 0), THREE_OUT(1, 3, 0), OPPOSITES(2, 2, 0), ADJACENTS(2, 2, 0);

  private final Integer[] sideTypes;
  private int definedSides;

  /**
   * Constructs a new PieceType with the given amount of each side type, in the order defined in
   * {@link SideType}
   *
   * @param sideTypes the amount of each side type, with the order and size of {@link SideType}; can
   *                  have null if the amount of a specific side type is undefined, e.g. in corners
   */
  PieceType(Integer... sideTypes) {
    this.sideTypes = sideTypes;
    for (Integer i : sideTypes) {
      if (i != null) {
        definedSides += i;
      }
    }
  }

  /**
   * Can the given piece be of this type?
   *
   * @param p the piece to be checked
   * @return true if the piece can be of this type, false if it cannot
   */
  public boolean canBeType(Piece p) {
    int[] sideTypeAmounts = new int[SideType.values().length];
    for (Direction dir : Direction.values()) {
      final Side side = p.getSide(dir);
      if (side != null) {
        sideTypeAmounts[side.getSideType().ordinal()]++;
      }
    }

    for (int i = 0; i < sideTypeAmounts.length; i++) {
      if (sideTypes[i] != null && sideTypes[i] < sideTypeAmounts[i]) {
        return false;
      }
    }

    return true;
  }
}
