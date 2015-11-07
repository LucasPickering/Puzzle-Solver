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

  private Piece binarySearch(@NotNull Direction dir, Piece p, @NotNull PieceType... pieceTypes) {
    Objects.requireNonNull(dir);
    Objects.requireNonNull(pieceTypes);
    if (p == null) {
      return null;
    }
    final List<Piece> fittedPieces = new ArrayList<>();
    final List<Piece> pieceList = pieceLists[dir.ordinal()];
    final int middle = Collections.binarySearch(pieceList, p, comparators[dir.ordinal()]);

    return null;
  }

  private int findFirst(Direction dir, Piece p, int middle) {
    int index = middle;
    for (int i = 0; true; i++) {
      if (index < 0) {
        return 0;
      }
      if (index >= size()) {
        return size() - 1;
      }
      if(comparators[dir.ordinal()].compare(pieceLists[dir.ordinal()].get(index), p) != 0){

      }
    }
  }

  @Override
  public Piece find(@NotNull Piece p) {
    Objects.requireNonNull(p);
    Piece foundPiece;
    for (Direction dir : Direction.values()) {
      foundPiece = binarySearch(dir, p, p.getPieceTypes());
      if (foundPiece != null) {
        if (p.equals(foundPiece)) {
          return foundPiece;
        } else {
          throw new IllegalStateException("Found a non-unique side, that's not possible!");
        }
      }
    }
    return null;
  }
}
