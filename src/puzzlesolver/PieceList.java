package puzzlesolver;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Collections;

import puzzlesolver.enums.Direction;

public final class PieceList {

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
   * Constructs a new {@code PieceList} with an initial capacity of ten.
   */
  public PieceList() {
    this(10);
  }

  /**
   * Constructs a new {@code PieceList} with the given initial capacity.
   *
   * @param capacity the initial capacity of the list (non-negative)
   * @throws IllegalArgumentException if capacity is negative
   */
  public PieceList(int capacity) {
    for (int i = 0; i < pieceLists.length; i++) {
      pieceLists[i] = new ArrayList<>(capacity);
      comparators[i] = new PieceComparator(Direction.values()[i]);
    }
  }

  /**
   * Constructs a new {@code PieceList} and adds all pieces in the given array.
   *
   * @param pieces the pieces to be added (non-null, does not contain null)
   */
  public PieceList(@NotNull Piece[] pieces) {
    this(pieces.length);
    addAll(pieces);
  }

  /**
   * Adds a piece to this list, while keeping all internal lists sorted.
   *
   * @param p the piece to be added
   */
  public void add(Piece p) {
    for (int i = 0; i < pieceLists.length; i++) {
      pieceLists[i].add(-(Collections.binarySearch(pieceLists[i], p, comparators[i]) + 1), p);
    }
  }

  /**
   * Adds all pieces in the given array
   *
   * @param pieces the pieces to be added (non-null, does not contain null)
   */
  public void addAll(@NotNull Piece[] pieces) {
    for (Piece p : pieces) {
      add(p);
    }
  }

  /**
   * Removes the given piece from this list, if it exists in the list.
   *
   * @param p the piece to be removed
   */
  public void remove(Piece p) {
    for (ArrayList<Piece> pieceList : pieceLists) {
      pieceList.remove(p);
    }
  }

  /**
   * Clears this list.
   */
  public void clear() {
    for (ArrayList<Piece> pieceList : pieceLists) {
      pieceList.clear();
    }
  }

  /**
   * Gets the piece at the given index if the list is sorted by the given direction.
   *
   * @param dir the direction of the internal list to be indexed (non-null)
   * @param i   the index of the item to be gotten
   * @return the piece at the given index in the list of the given direction
   * @throws NullPointerException      if {@code dir} is {@code null}
   * @throws IndexOutOfBoundsException if {@code i} is out of the bounds of this list
   */
  public Piece get(@NotNull Direction dir, int i) {
    if (i < 0 || i >= pieceLists[0].size()) {
      throw new IndexOutOfBoundsException("i is out of bounds");
    }
    return pieceLists[dir.ordinal()].get(i);
  }

  /**
   * Gets the first item in this list when sorted by the given direction.
   *
   * @param dir the direction to sort by (non-null)
   * @return the first item in the list
   * @throws NullPointerException      if {@code dir} is {@code null}
   * @throws IndexOutOfBoundsException if this list does not have any items
   */
  public Piece first(@NotNull Direction dir) {
    return get(dir, 0);
  }

  /**
   * Gets the size of this list.
   *
   * @return the size of this list
   */
  public int size() {
    return pieceLists[0].size();
  }

  /**
   * Performs a binary search on this list if sorted by the given direction to find the given
   * piece.
   *
   * @param dir the direction to sort this list by (non-null)
   * @param p   the piece to be found
   * @return the index of the search key, if it is contained in the list; otherwise, (-(insertion
   * point) - 1). The insertion point is defined as the point at which the key would be inserted
   * into the list: the index of the first element greater than the key, or list.size() if all
   * elements in the list are less than the specified key. Note that this guarantees that the return
   * value will be >= 0 if and only if the key is found.
   * @throws NullPointerException if either {@code dir} is {@code null}
   */
  public int binarySearch(@NotNull Direction dir, Piece p) {
    return Collections.binarySearch(pieceLists[dir.ordinal()], p, comparators[dir.ordinal()]);
  }

  /**
   * Does this list contain the given piece?
   *
   * @param p the piece to be found
   * @return true if this list contains p, false otherwise
   * @throws NullPointerException if {@code p} is {@code null}
   */
  public boolean contains(Piece p) {
    return binarySearch(Direction.NORTH, p) > 0;
  }
}
