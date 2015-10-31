package puzzlesolver.simple;

import puzzlesolver.Piece;
import puzzlesolver.PieceList;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;
import puzzlesolver.Solver;

public final class SimpleSolver implements Solver {

  private int width;
  private int height;
  private Piece[][] solution;

  @Override
  public Piece[][] solve(Piece[] pieces) {
    final PieceList pieceList = new SimplePieceList(pieces.length);
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
    placeEdges(pieceList);

    return solution;
  }

  private void placeCorners(PieceList pieces) {
    Piece piece;
    while ((piece = pieces.first(Direction.NORTH)).getPieceType() == PieceType.CORNER) {
      final int x = piece.getSide(Direction.WEST).getSideType().isFlat() ? 0 : width - 1;
      final int y = piece.getSide(Direction.NORTH).getSideType().isFlat() ? 0 : height - 1;
      solution[x][y] = piece;
      pieces.remove(piece);
    }
  }

  private void placeEdges(PieceList pieces) {
    for (int x = 1; x < width - 1; x++) {
      for (int y = 0; y < height; y += height - 1) {
        final Piece piece = pieces
            .search(Direction.WEST, solution[x - 1][y].getSide(Direction.EAST), PieceType.EDGE);
        solution[x][y] = piece;
        pieces.remove(piece);
      }
    }
    for (int x = 0; x < height; x += height - 1) {
      for (int y = 1; y < width - 1; y++) {
        final Piece piece = pieces
            .search(Direction.NORTH, solution[x][y - 1].getSide(Direction.SOUTH), PieceType.EDGE);
        solution[x][y] = piece;
        pieces.remove(piece);
      }
    }
  }
}
