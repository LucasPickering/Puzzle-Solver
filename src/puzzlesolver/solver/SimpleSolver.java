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

  /**
   * Gets the width of a puzzle with the given perimeter and area.
   *
   * @param perimeter the amount of edge/corner pieces in the puzzle (positive)
   * @param area      the amount of total pieces in the puzzle (positive)
   * @return the width of the puzzle, in pieces
   * @throws IllegalArgumentException if no width can be found for the given perimeter and area
   */
  private int getWidth(int perimeter, int area) {
    final int helper = (perimeter + 4) / 2;
    final double width = (helper + Math.sqrt(helper * helper - 4 * area)) / 2;
    final int roundedWidth = (int) (width + 0.5D);
    final double error = Math.abs(width - roundedWidth);
    if (error > 0.1D) {
      throw new IllegalArgumentException(String.format("Error is %f for perimeter %d and area %d",
          error, perimeter, area));
    }
    return roundedWidth;
  }

  /**
   * Gets the height of a puzzle with the given width and area.
   *
   * @param width the width of the puzzle, in pieces (positive)
   * @param area  the amount of total pieces in the puzzle (positive)
   * @return the height of the puzzle, in pieces
   * @throws IllegalArgumentException if no puzzle with the given area and width exists, i.e. {@code
   *                                  area} is not divisible by {@code width}
   */
  private int getHeight(int width, int area) {
    if (area % width != 0) {
      throw new IllegalArgumentException(String.format("Area (%d) is not divisible by width (%d)",
          area, width));
    }
    return area / width;
  }
}
