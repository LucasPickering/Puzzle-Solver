package puzzlesolver.solver;

import org.junit.Test;

import java.util.ArrayList;

import puzzlesolver.Piece;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;
import puzzlesolver.enums.SideType;

import static org.junit.Assert.assertEquals;

public final class SimpleSolver implements Solver {

  @Override
  public Piece[][] solve(Piece[] pieces) {
    final ArrayList<Piece> pieceList = new ArrayList<>();
    int edges = 0;
    for (Piece piece : pieces) {
      if (piece.getPieceType() == PieceType.EDGE) {
        edges++;
      }
      pieceList.add(piece);
    }
    pieceList.sort(null); // Sort the list by its natural ordering

    final int width = getWidth(edges + 4, pieces.length);
    final int height = getHeight(width, pieces.length);
    final Piece[][] solution = new Piece[width][height];

    // Place the corners and edges
    while (pieceList.size() > 0) {
      final Piece piece = pieceList.get(0);
      switch(piece.getPieceType()){
        case CORNER:
          final int x = piece.getSide(Direction.WEST).getSideType().isFlat() ? 0 : width - 1;
          final int y = piece.getSide(Direction.NORTH).getSideType().isFlat() ? 0 : height - 1;
          solution[x][y] = piece;
          pieceList.remove(0);
          break;
        case EDGE:

          break;
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

  public class SimpleSolverInternalTests {

    @Test
    public void testGetWidth() {
      assertEquals(50, getWidth(116, 500));
    }

    @Test
    public void testGetHeight() {
      assertEquals(10, getHeight(50, 500));
    }
  }
}
