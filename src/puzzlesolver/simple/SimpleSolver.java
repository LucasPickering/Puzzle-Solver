package puzzlesolver.simple;

import puzzlesolver.Piece;
import puzzlesolver.PieceList;
import puzzlesolver.Solver;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;

public final class SimpleSolver implements Solver {

  private int width;
  private int height;
  private Piece[][] solution;

  @Override
  public Piece[][] solve(Piece[] pieces) {
    final PieceList pieceList = new SimplePieceList(pieces.length);
    int edges = 0;
    for (Piece piece : pieces) {
      if (piece.definitelyType(PieceType.EDGE)) {
        edges++;
      }
      pieceList.add(piece);
    }

    width = getWidth(edges + 4, pieces.length);
    height = getHeight(width, pieces.length);
    solution = new Piece[width][height];

    placeCorners(pieceList);
    placeRest(pieceList);

    return solution;
  }

  /**
   * Makes a piece as accurately as possible to fit at the given x and y, using the pieces adjacent
   * to that spot.
   *
   * @param x the x-coord of the piece [0, width)
   * @param y the y-coord of the piece [0, height)
   * @return the constructed piece
   */
  private Piece makePiece(int x, int y) {
    Piece.Builder builder = new Piece.Builder();
    for (Direction dir : Direction.values()) { // For each side
      final int dirX = x + dir.x;
      final int dirY = y + dir.y;

      if (dirX < 0 || dirX >= width || dirY < 0 || dirY >= height) {
        // If x or y is out of bounds, make a flat side
        builder.setSide(null, dir); // TODO: Figure out a way to generate a flat side
      } else if (solution[dirX][dirY] != null) {
        // If there is an adjacent piece, get its neighboring side
        builder.setSide(solution[dirX][dirY].getSide(dir.opposite()), dir);
      }
    }
    return builder.build();
  }

  private void placeCorners(PieceList pieces) {
    Piece piece;
    while ((piece = pieces.first(Direction.NORTH)).definitelyType(PieceType.CORNER)) {
      final int x = piece.getSide(Direction.WEST).getSideType().isFlat() ? 0 : width - 1;
      final int y = piece.getSide(Direction.NORTH).getSideType().isFlat() ? 0 : height - 1;
      solution[x][y] = piece;
      pieces.remove(piece);
    }
  }

  private void placeRest(PieceList pieces) {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        final Piece piece = pieces.find(makePiece(x, y));
        solution[x][y] = piece;
        pieces.remove(piece);
      }
    }
  }
}
