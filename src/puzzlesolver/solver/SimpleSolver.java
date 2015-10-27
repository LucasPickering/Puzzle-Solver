package puzzlesolver.solver;

import puzzlesolver.Piece;
import puzzlesolver.PieceList;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;

public final class SimpleSolver implements Solver {

  private int width;
  private int height;
  private Piece[][] solution;

  @Override
  public Piece[][] solve(Piece[] pieces) {
    final PieceList pieceList = new PieceList(pieces.length);
    int edges = 0;
    for (Piece piece : pieces) {
      if (piece.getPieceType() == PieceType.EDGE) {
        edges++;
      }
      pieceList.add(piece);
    }

    width = getWidth(edges + 4, pieces.length);
    height = getHeight(width, pieces.length);
    solution = new Piece[width][height];

    placeCorners(pieceList);

    return solution;
  }

  private void placeCorners(PieceList pieceList) {
    Piece piece;
    while ((piece = pieceList.first(Direction.NORTH)).getPieceType() == PieceType.CORNER) {
      final int x = piece.getSide(Direction.WEST).getSideType().isFlat() ? 0 : width - 1;
      final int y = piece.getSide(Direction.NORTH).getSideType().isFlat() ? 0 : height - 1;
      solution[x][y] = piece;
      pieceList.remove(piece);
    }
  }


}
