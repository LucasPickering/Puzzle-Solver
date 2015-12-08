package puzzlesolver.piecelist;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import puzzlesolver.Funcs;
import puzzlesolver.Piece;
import puzzlesolver.PieceComparator;
import puzzlesolver.constants.Constants;
import puzzlesolver.enums.Direction;

public class SimplePieceList implements PieceList {

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
   * @param pieceLists the lists to construct this list from; this array should satisfy all invariants
   *                   for {@code this.pieceLists}
   */
  private SimplePieceList(@NotNull ArrayList<Piece>[] pieceLists) {
    Objects.requireNonNull(pieceLists);
    this.pieceLists = pieceLists;
  }

  @Override
  public void add(Piece p) {
    for (int i = 0; i < pieceLists.length; i++) {
      final int destination = Collections.binarySearch(pieceLists[i], p, comparators[i]);
      pieceLists[i].add(destination < 0 ? -(destination + 1) : destination, p);
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

  @Override
  public Piece find(@NotNull Piece p) {
    Objects.requireNonNull(p);
    int midIndex;
    for (Direction dir : Direction.values()) {
      if (!p.sideNull(dir)) {
        // Find any piece where two sides compare to 0 (somewhat equal)
        final int dirOrd = dir.ordinal();
        midIndex = Collections.binarySearch(pieceLists[dirOrd], p, comparators[dirOrd]);
        if (midIndex >= 0) { // If that piece was found
          final int leftIndex = Funcs.expSearch(pieceLists[dirOrd], comparators[dirOrd],
                                                midIndex, true);
          final int rightIndex = Funcs.expSearch(pieceLists[dirOrd], comparators[dirOrd],
                                                 midIndex, false);
          for (int i = leftIndex; i <= rightIndex; i++) {
            final Piece p2 = pieceLists[dirOrd].get(i);
            if (Arrays.binarySearch(p.getPieceTypes(), p2.getPieceType()) >= 0 && p.maybeEquals(p2)) {
              return p2;
            }
          }
        }
      }
    }
    return null;
  }
}
