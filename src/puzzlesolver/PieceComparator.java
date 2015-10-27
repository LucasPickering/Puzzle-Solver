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
  public int compare(Piece o1, Piece o2) {
    if (o1 == null || o2 == null) {
      throw new NullPointerException("This comparator does not accept null parameters");
    }
    return o1.getSide(dir).compareTo(o2.getSide(dir));
  }
}