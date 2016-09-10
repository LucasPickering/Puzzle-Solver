package puzzlesolver.piecelist;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.Objects;

import puzzlesolver.Piece;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;

/**
 * A {@link PieceList} where pieces are stored in a collection of other PieceLists. Pieces are sorted
 * into one of these PieceLists by their piece types, with one type per list. Access indices start at
 * 0 (inclusive) and end at the sum of all internal piece lists (exclusive). Indices continue through
 * each internal list in the order that piece types appear in {@link PieceType}.
 */
public class PieceTypePieceList extends SimplePieceList {

  private PieceList[] pieceLists = new PieceList[PieceType.values().length];

  public PieceTypePieceList() {
    this(10);
  }

  public PieceTypePieceList(int initialCapacity) {
    final int listSize = initialCapacity / PieceType.values().length;
    for (PieceType pieceType : PieceType.values()) {
      pieceLists[pieceType.ordinal()] = new SimplePieceList(listSize);
    }
  }

  @Override
  public void add(Piece p) {
    Objects.requireNonNull(p);
    pieceLists[p.getPieceType().ordinal()].add(p);
  }

  @Override
  public boolean remove(Piece p) {
    for (PieceList pieceList : pieceLists) {
      if (pieceList.remove(p)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void clear() {
    for (PieceList pieceList : pieceLists) {
      pieceList.clear();
    }
  }

  @Override
  public Piece get(@NotNull Direction dir, int i) {
    for (PieceType pieceType : PieceType.values()) {
      Piece p;
      if ((p = pieceLists[pieceType.ordinal()].get(dir, i)) != null) {
        return p;
      }
    }
    return null;
  }

  @Override
  public int size() {
    return Arrays.stream(pieceLists).mapToInt(PieceList::size).sum();
  }

  @Override
  public Piece find(@NotNull Piece p) {
    for (PieceType pieceType : p.getPieceTypes()) {
      Piece foundPiece;
      if ((foundPiece = pieceLists[pieceType.ordinal()].find(p)) != null) {
        return foundPiece;
      }
    }
    return null;
  }

  @Override
  public PieceList sublistByType(PieceType pieceType) {
    Objects.requireNonNull(pieceType);
    return pieceLists[pieceType.ordinal()];
  }
}
