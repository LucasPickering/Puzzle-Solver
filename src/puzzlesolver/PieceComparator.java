package puzzlesolver;

import java.util.Comparator;

import puzzlesolver.enums.Direction;

public class PieceComparator implements Comparator<Piece> {

  private final Direction dir;

  /**
   * Constructs a new {@code PieceComparator} that compares pieces by their sides in the given
   * direction.
   *
   * @param dir the direction of the sides that will be compared (non-null)
   * @throws NullPointerException if {@code dir} is {@code null}
   */
  public PieceComparator(Direction dir) {
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
    return p1.getSide(dir).compareTo(p2.getSide(dir));
  }

  /**
   * Checks if {@param p1} is compatible with (can fit with) the {@param p2}.
   *
   * @param p1 the first {@link Side}
   * @param p2 the second {@link Side}
   * @return {@link #compare}-esque number designating compatibility (0 = compatible).
   */
  public int compatibility(Piece p1, Piece p2) {
    if (p1 == null || p2 == null) {
      throw new NullPointerException("This comparator does not accept null parameters");
    }
    return p1.getSide(dir).compatibilityWith(p2.getSide(dir));
  }
}