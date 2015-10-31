package puzzlesolver;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import puzzlesolver.enums.Direction;
import puzzlesolver.side.Side;

public final class SimplePieceList implements PieceList {

  /*
   * INVARIANTS
   *
   * pieceLists
   * - Non-null
   * - Contents are non-null
   * - All 4 lists always have all the same elements; they only vary in order
   * - All 4 lists are always sorted using a PieceComparator for that list's corresponding direction
   * - Contents of all internal lists are non-null
   *
   * comparators
   * - Non-null
   * - Contents are non-null
   */

  @SuppressWarnings("unchecked")
  private final ArrayList<Piece>[] pieceLists = new ArrayList[4];
  private final PieceComparator[] comparators = new PieceComparator[4];

  /**
   * Constructs a new {@code SimplePieceList} with an initial capacity of ten.
   */
  public SimplePieceList() {
    this(10);
  }

  /**
   * Constructs a new {@code SimplePieceList} with the given initial capacity.
   *
   * @param capacity the initial capacity of the list (non-negative)
   * @throws IllegalArgumentException if capacity is negative
   */
  public SimplePieceList(int capacity) {
    for (int i = 0; i < pieceLists.length; i++) {
      pieceLists[i] = new ArrayList<>(capacity);
      comparators[i] = new PieceComparator(Direction.values()[i]);
    }
  }

  /**
   * Constructs a new {@code SimplePieceList} and adds all pieces in the given array.
   *
   * @param pieces the pieces to be added (non-null, does not contain null)
   */
  public SimplePieceList(@NotNull Piece[] pieces) {
    this(pieces.length);
    addAll(pieces);
  }

  @Override
  public void add(Piece p) {
    for (int i = 0; i < pieceLists.length; i++) {
      pieceLists[i].add(-(Collections.binarySearch(pieceLists[i], p, comparators[i]) + 1), p);
    }
  }

  @Override
  public void addAll(@NotNull Piece[] pieces) {
    for (Piece p : pieces) {
      add(p);
    }
  }

  @Override
  public void remove(Piece p) {
    for (ArrayList<Piece> pieceList : pieceLists) {
      pieceList.remove(p);
    }
  }

  @Override
  public void clear() {
    for (ArrayList<Piece> pieceList : pieceLists) {
      pieceList.clear();
    }
  }

  @Override
  public Piece get(@NotNull Direction dir, int i) {
    if (i < 0 || i >= pieceLists[0].size()) {
      throw new IndexOutOfBoundsException("i is out of bounds");
    }
    return pieceLists[dir.ordinal()].get(i);
  }

  @Override
  public Piece first(@NotNull Direction dir) {
    return get(dir, 0);
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public int size() {
    return pieceLists[0].size();
  }

  @Override
  public int binarySearch(@NotNull Direction dir, Side s) {
    return Collections.binarySearch(pieceLists[dir.ordinal()].stream().map(p -> p.getSide(dir))
        .collect(Collectors.toList()), s);
  }

  @Override
  public boolean containsSide(Side s) {
    return binarySearch(Direction.NORTH, s) > 0;
  }
}
