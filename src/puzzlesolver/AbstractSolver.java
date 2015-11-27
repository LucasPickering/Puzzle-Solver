package puzzlesolver;

import puzzlesolver.constants.Constants;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;
import puzzlesolver.simple.SimplePieceList;
import puzzlesolver.simple.SimpleSide;

public abstract class AbstractSolver implements Solver {

  protected int width;
  protected int height;
  protected PieceList unplacedPieces;
  protected Piece[][] solution;
  protected int x;
  protected int y;

  @Override
  public void init(Piece[] pieces) {
    unplacedPieces = new SimplePieceList(pieces.length);
    int edges = 0;
    for (Piece piece : pieces) {
      if (piece.definitelyType(PieceType.EDGE)) {
        edges++;
      }
      unplacedPieces.add(piece);
    }

    width = getWidth(edges + 4, pieces.length);
    height = getHeight(width, pieces.length);
    solution = new Piece[width][height];
  }

  @Override
  public PieceList getUnplacedPieces() {
    return unplacedPieces;
  }

  @Override
  public Piece[][] getSolution() {
    return solution;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
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
    final int helper = (perimeter + 4) / 2; // This is used a lot
    final double width = (helper + Math.sqrt(helper * helper - 4 * area)) / 2; // From quadratic form.
    final int roundedWidth = (int) (width + 0.5D); // Cast with rounding
    final double error = Math.abs(width - roundedWidth); // Check error, should be minimal
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

  /**
   * Makes a piece as accurately as possible to fit at the given x and y, using the pieces adjacent to
   * that spot.
   *
   * @param x the x-coord of the piece [0, width)
   * @param y the y-coord of the piece [0, height)
   * @return the constructed piece
   */
  protected Piece makePiece(int x, int y) {
    Piece.Builder builder = new Piece.Builder();
    for (Direction dir : Direction.values()) { // For each side
      final int dirX = x + dir.x;
      final int dirY = y + dir.y;

      if (dirX < 0 || dirX >= width || dirY < 0 || dirY >= height) {
        // If x or y is out of bounds, make a flat side
        builder.setSide(new SimpleSide(new Point(0d, 0d),
                                       new Point(Constants.SIDE_LENGTH, 0d)), dir);
      } else if (solution[dirX][dirY] != null) {
        // If there is an adjacent piece, get its neighboring side
        builder.setSide(solution[dirX][dirY].getSide(dir.opposite()).inverse(), dir);
      }
    }
    return builder.build();
  }

  /**
   * Rotates the solution clockwise 90 degrees. Only rotates if all non-null pieces would fit into the
   * rotated solution.
   */
  protected void rotateSolution() {

  }
}
