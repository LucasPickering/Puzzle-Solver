package puzzlesolver.solver;

import puzzlesolver.Piece;
import puzzlesolver.PieceNotFoundException;
import puzzlesolver.Point;
import puzzlesolver.constants.Constants;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;
import puzzlesolver.piecelist.PieceList;
import puzzlesolver.piecelist.SimplePieceList;
import puzzlesolver.side.SimpleSide;

public class SimpleSolver implements Solver {

  protected class State {

    protected PieceList unplacedPieces;
    protected Piece[][] solution;
    protected int x, y;

    protected State(int width, int height, PieceList unplacedPieces) {
      solution = new Piece[width][height];
      this.unplacedPieces = unplacedPieces;
    }

    protected int width() {
      return solution.length;
    }

    protected int height() {
      return solution[0].length;
    }

    protected void placePiece(Piece piece) {
      solution[x][y] = piece;
    }

    protected void incr() {
      if (++x >= width()) { // Increment x, if the row is done, go to the next row
        x = 0;
        y++;
      }
    }
  }

  protected State state;

  @Override
  public void init(Piece[] pieces) {
    PieceList unplacedPieces = new SimplePieceList(pieces.length);
    int edges = 0;
    for (Piece piece : pieces) {
      if (piece.definitelyType(PieceType.EDGE)) {
        edges++;
      }
      unplacedPieces.add(piece);
    }

    int width = getWidth(edges + 4, pieces.length);
    int height = getHeight(width, pieces.length);
    state = new State(width, height, unplacedPieces);
  }

  @Override
  public boolean nextStep() throws PieceNotFoundException {
    // If this is the first piece, find the first corner in the list and place it
    if (state.x == 0 && state.y == 0) {
      placeCorner(state); // Place the first piece in the puzzle
    } else if (state.x < state.width() && state.y < state.height()) {
      placeNextPiece(state); // Place the next piece in the puzzle
    }
    state.incr(); // Increment up to the next piece
    return done(state); // Return false if we need to keep going, true if we're done
  }

  @Override
  public PieceList getUnplacedPieces() {
    return state.unplacedPieces;
  }

  @Override
  public Piece[][] getSolution() {
    return state.solution;
  }

  @Override
  public int getX() {
    return state.x;
  }

  @Override
  public int getY() {
    return state.y;
  }

  /**
   * Gets the width of a puzzle with the given perimeter and area.
   *
   * @param perimeter the amount of edge/corner pieces in the puzzle (positive)
   * @param area      the amount of total pieces in the puzzle (positive)
   * @return the width of the puzzle, in pieces
   * @throws IllegalArgumentException if no width can be found for the given perimeter and area
   */
  protected int getWidth(int perimeter, int area) {
    final int helper = (perimeter + 4) / 2; // I DON'T KNOW HOW MANY TIMES IT'S USED LOOK YOURSELF
    final double width = (helper + Math.sqrt(helper * helper - 4 * area)) / 2; // Quadratic formula
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
  protected int getHeight(int width, int area) {
    if (area % width != 0) {
      throw new IllegalArgumentException(String.format("Area (%d) is not divisible by width (%d)",
                                                       area, width));
    }
    return area / width;
  }

  /**
   * Makes a piece as accurately as possible to fit at the state's x and y, using the pieces
   * adjacent to that spot.
   *
   * @param state the current state of the puzzle/solver
   * @return the constructed piece
   */
  protected Piece makePiece(State state) {
    return makePiece(state, state.x, state.y);
  }

  /**
   * Makes a piece as accurately as possible to fit at the given x and y, using the pieces adjacent
   * to that spot.
   *
   * @param state the current state of the puzzle/solver
   * @param x     the x position to make a piece for
   * @param y     the y position to make a piece for
   * @return the constructed piece
   */
  protected Piece makePiece(State state, int x, int y) {
    Piece.Builder builder = new Piece.Builder();
    for (Direction dir : Direction.values()) { // For each side
      final int dirX = x + dir.x;
      final int dirY = y + dir.y;

      if (dirX < 0 || dirX >= state.width() || dirY < 0 || dirY >= state.height()) {
        // If x or y is out of bounds, make a flat side
        builder.setSide(new SimpleSide(new Point(0d, 0d),
                                       new Point(Constants.SIDE_LENGTH, 0d)), dir);
      } else if (state.solution[dirX][dirY] != null) {
        // If there is an adjacent piece, get its neighboring side
        builder.setSide(state.solution[dirX][dirY].getSide(dir.opposite()).inverse(), dir);
      }
    }
    return builder.build();
  }

  /**
   * Place the first corner down in an empty solution. Undefined results if you call this when
   * there is already a piece in the puzzle!
   *
   * @param state the current state of the puzzle/solver
   */
  protected void placeCorner(State state) throws PieceNotFoundException {
    for (int i = 0; i < state.unplacedPieces.size(); i++) {
      Piece piece = state.unplacedPieces.get(Direction.NORTH, i);
      if (piece.definitelyType(PieceType.CORNER) && piece.getSide(Direction.NORTH).isFlat() &&
          piece.getSide(Direction.WEST).isFlat()) {
        state.placePiece(piece); // Put the piece in the solution
        state.unplacedPieces.remove(piece); // Take it out of the bag of unused pieces
        break;
      }
    }
  }

  /**
   * Place the next piece in the puzzle. Undefined results if you call this when there isn't
   * already at least one piece in the puzzle!
   *
   * @param state the current state of the puzzle/solver
   */
  protected void placeNextPiece(State state) throws PieceNotFoundException {
    final Piece piece = state.unplacedPieces.find(makePiece(state));
    state.placePiece(piece); // Put the piece in the solution
    state.unplacedPieces.remove(piece); // Take it out of the bag of unused pieces
  }

  /**
   * Is the puzzle done?
   *
   * @param state the current state of the puzzle
   * @return true if the puzzle is done, false if it needs more work
   */
  protected boolean done(State state) {
    return state.y >= state.height(); // If we're past the last row, we're done
  }
}
