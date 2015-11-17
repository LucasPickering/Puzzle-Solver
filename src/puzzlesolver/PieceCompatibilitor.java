package puzzlesolver;

import java.util.Comparator;

import puzzlesolver.enums.Direction;

public class PieceCompatibilitor implements Comparator<Piece> {

  private final Direction dir;

  /**
   * Constructs a new {@code PieceCompatibilitor} that compares pieces by their sides in the given
   * direction. The side of one piece is compared with the inverse of the side of the other piece
   * using {@link Side#compatibilityWith}.
   *
   * @param dir the direction of the sides that will be compared (non-null)
   * @throws NullPointerException if {@code dir} is {@code null}
   */
  public PieceCompatibilitor(Direction dir) {
    if (dir == null) {
      throw new NullPointerException("dir cannot be null");
    }
    this.dir = dir;
  }

  @Override
  public int compare(Piece p1, Piece p2) {
    if (p1 == null || p2 == null) {
      throw new NullPointerException("This comparator does not accept null parameters");
    }
    return p1.getSide(dir).compatibilityWith(p2.getSide(dir));
  }

}
