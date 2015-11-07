package puzzlesolver.simple;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import puzzlesolver.Constants;
import puzzlesolver.Piece;
import puzzlesolver.PieceComparator;
import puzzlesolver.PieceList;
import puzzlesolver.Side;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;

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

  private final ArrayList<Piece>[] pieceLists;
  private final PieceComparator[] comparators = new PieceComparator[Constants.NUM_SIDES];

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
  @SuppressWarnings("unchecked")
  public SimplePieceList(int capacity) {
    pieceLists = new ArrayList[4];
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

  /**
   * Constructs a new {@code SimplePieceList} from the given array of 4 lists of pieces.
   *
   * @param pieceLists the lists to construct this list from; this array should satisfy all
   *                   invariants for {@code this.pieceLists}
   */
  private SimplePieceList(@NotNull ArrayList<Piece>[] pieceLists) {
    Objects.requireNonNull(pieceLists);
    this.pieceLists = pieceLists;
  }

  @Override
  public void add(Piece p) {
    for (int i = 0; i < pieceLists.length; i++) {
      pieceLists[i].add(-(Collections.binarySearch(pieceLists[i], p, comparators[i]) + 1), p);
    }
  }

  @Override
  public void addAll(@NotNull Piece[] pieces) {
    Objects.requireNonNull(pieces);
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
    Objects.requireNonNull(dir);
    if (i < 0 || i >= pieceLists[0].size()) {
      throw new IndexOutOfBoundsException("i is out of bounds");
    }
    return pieceLists[dir.ordinal()].get(i);
  }

  @Override
  public Piece first(@NotNull Direction dir) {
    Objects.requireNonNull(dir);
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

  private int binarySearch(Direction dir, Piece p, PieceType... pieceTypes) {
    return Collections.binarySearch(pieceLists[dir.ordinal()], p, comparators[dir.ordinal()]);
  }

  /**
   * Searches the list sorted by the given direciton for the last piece that matches p, in the given
   * direction. Starts at index, and moves down/up the list exponentially until it finds the first
   * piece that doesn't match p.
   *
   * @param dir   the direction to sort and compare by
   * @param p     the piece to be searched for
   * @param index the starting index
   * @param left  true to search left (lesser indices), false to search right (greater indices)
   * @return the index of the last piece in the list that matches p
   */
  private int expSearch(Direction dir, Piece p, int index, boolean left) {
    for (int i = 0; true; i++) {
      // The index of the next element to check; grows exponentially
      int nextIndex = index + (int) (Math.pow(2, i) + 0.5d) * (left ? -1 : 1);
      final boolean oob = left ? nextIndex < 0 : nextIndex >= size();

      // If the nextIndex is out of bounds or the element at that index doesn't match p
      if (oob || compare(dir, nextIndex, p) != 0) {
        // If this is the first iteration in the loop (nextIndex == index - 1), stop
        if (i == 0) {
          return index; // Return this index
        }
        i = 0; // Reset i to reset the exponentiation
      } else { // If we should keep exponentially looking
        index = nextIndex; // Set current index to next and let it loop again
      }
    }
  }

  /**
   * Compares the given piece to the piece at the given index in the list sorted by the given
   * direction. This comparison is done by comparing the side in the given direction each piece.
   *
   * @param dir   the direction to sort and compare by
   * @param index the index of the piece in the list being compared
   * @param p     the other piece being compared
   * @return negative if the piece at index is less than p, 0 if equal, and positive if greater
   */
  private int compare(Direction dir, int index, Piece p) {
    return comparators[dir.ordinal()].compare(pieceLists[dir.ordinal()].get(index), p);
  }

  @Override
  public Piece find(@NotNull Piece p) {
    Objects.requireNonNull(p);
    int middleIndex;
    for (Direction dir : Direction.values()) {
      // Find any piece where two sides compare to 0 (somewhat equal)
      middleIndex = binarySearch(dir, p, p.getPieceTypes());
      if (middleIndex >= 0) { // If that piece was found
        final int leftIndex = expSearch(dir, p, middleIndex, true);
        final int rightIndex = expSearch(dir, p, middleIndex, false);
        for (int i = leftIndex; i <= rightIndex; i++) {
          final Piece iPiece = pieceLists[dir.ordinal()].get(i);
          if (p.equals(iPiece)) {
            return iPiece;
          }
        }
      }
    }
    return null;
  }
}
