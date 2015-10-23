package puzzlesolver.solver;

import java.util.ArrayList;

import puzzlesolver.Piece;
import puzzlesolver.side.Side;

public final class SimpleSolver implements Solver {

  @Override
  public Piece[][] solve(Piece[] pieces) {
    final int width = getWidth(0, pieces.length);
    final int height = getHeight(width, pieces.length);
    final Piece[][] solution = new Piece[width][height];
    @SuppressWarnings("unchecked")
    ArrayList<Piece>[] groupedPieces = new ArrayList[Piece.PieceType.values().length];

    for (Piece piece : pieces) {
      final int type = piece.getPieceType().ordinal();
      if (groupedPieces[type] == null) {
        groupedPieces[type] = new ArrayList<>();
      }
      groupedPieces[type].add(piece);
    }
    for (ArrayList<Piece> pieceList : groupedPieces) {
      pieceList.sort(null);
    }

    // Place the corners first
    for (Piece corner : groupedPieces[Piece.PieceType.CORNER.ordinal()]) {
      if (corner.getSide(Piece.Direction.NORTH).getSideType() == Side.SideType.FLAT) {
        if (corner.getSide(Piece.Direction.WEST).getSideType() == Side.SideType.FLAT) {
          solution[0][0] = corner;
        } else {
          solution[width - 1][0] = corner;
        }
      } else {
        if (corner.getSide(Piece.Direction.WEST).getSideType() == Side.SideType.FLAT) {
          solution[0][height - 1] = corner;
        } else {
          solution[width - 1][height - 1] = corner;
        }
      }
    }

    return solution;
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
