package puzzlesolver;

import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;

public class PotentialPiece {

  private final Side[] sides = new Side[4];

  /**
   * Gets a clone of the side of this piece in the given direction.
   *
   * @param dir the direction of the piece to be retrieved
   * @return a clone of the side in the given direction
   */
  public Side getSide(Direction dir) {
    return sides[dir.ordinal()].copy();
  }

  /**
   * Sets
   * @param dir
   * @param s
   */
  public void setSide(Direction dir, Side s) {
    sides[dir.ordinal()] = s;
  }

  public PieceType[] getPieceTypes(){

  }

  private void findPieceTypes(){

  }
}
