package puzzlesolver;

import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;
import puzzlesolver.simple.SimplePieceList;

public abstract class AbstractSolver implements Solver {

  protected int width;
  protected int height;
  protected PieceList unplacedPieces;
  protected Piece[][] solution;

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
    for (int i = 0; i < unplacedPieces.size(); i++) {
      System.out.println(unplacedPieces.get(Direction.NORTH, i).getSide(Direction.NORTH));
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
