package puzzlesolver.solver;

import puzzlesolver.Funcs;
import puzzlesolver.Pair;
import puzzlesolver.Piece;
import puzzlesolver.PieceNotFoundException;
import puzzlesolver.Point;
import puzzlesolver.constants.Constants;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;
import puzzlesolver.piecelist.PieceList;
import puzzlesolver.side.SimpleSide;

public abstract class AbstractSolver implements Solver {

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

    protected void incr() {
      if (++x >= width()) { // Increment x, if the row is done, go to the next row
        x = 0;
        y++;
      }
    }

    @Override
    public String toString() {
      final int unplacedAmount = unplacedPieces == null ? 0 : unplacedPieces.size();
      final String solutionString = solution == null ? "null" : solution.length + "x" +
                                                                solution[0].length;
      return String.format("[unplacedPieces: %d, solution: %s, x: %d, y: %d]",
                           unplacedAmount, solutionString, x, y);
    }
  }

  protected State state;
  private int totalPieces;
  private int placedPieces;

  @Override
  public void init(Piece[] pieces) {
    PieceList unplacedPieces = makePieceList(pieces);

    // Add all pieces to the list, and count edge pieces as we go
    int edges = 0;
    for (Piece piece : pieces) {
      if (piece.definitelyType(PieceType.EDGE)) {
        edges++;
      }
      unplacedPieces.add(piece);
    }

    totalPieces = unplacedPieces.size(); // Keep this for later
    placedPieces = 0; // Make sure this gets reset

    // Calculate dimensions of the puzzle. Use number of edges + 4 corners for perimeter.
    final Pair<Integer, Integer> dimensions = Funcs.getDimensions(edges + 4, pieces.length);
    state = new State(dimensions.left, dimensions.right, unplacedPieces);
  }

  @Override
  public void nextStep() throws PieceNotFoundException {
    if (!done()) {
      // If this is the first piece, find the first corner in the list and place it
      if (state.x == 0 && state.y == 0) {
        placeCorner(state); // Place the first piece in the puzzle
      } else {
        placeNextPiece(state); // Place the next piece in the puzzle
      }
      state.incr(); // Increment up to the next piece
      placedPieces++;
    }
  }

  @Override
  public boolean done() {
    return state.unplacedPieces.isEmpty();
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
  public int getTotalPieces() {
    return totalPieces;
  }

  @Override
  public int getPiecesPlaced() {
    return placedPieces;
  }

  /**
   * Turn the given array of pieces into a {@link PieceList}. This list will be used as the set
   * of pieces to pull from when solving the puzzle.
   *
   * @param pieces the set of pieces to be used
   * @return a {@link PieceList} of the same pieces
   */
  protected abstract PieceList makePieceList(Piece[] pieces);

  /**
   * Place the first corner down in an empty solution. Undefined results if you call this when
   * there is already a piece in the puzzle!
   *
   * @param state the current state of the puzzle/solver
   */
  protected abstract void placeCorner(State state) throws PieceNotFoundException;

  /**
   * Place the next piece in the puzzle. Undefined results if you call this when there isn't
   * already at least one piece in the puzzle!
   *
   * @param state the current state of the puzzle/solver
   */
  protected abstract void placeNextPiece(State state) throws PieceNotFoundException;

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

  protected void placePiece(State state, Piece piece) {
    state.solution[state.x][state.y] = piece; // Put the piece in the solution
    state.unplacedPieces.remove(piece); // Remove the piece from the bag of unplaced ones
    placedPieces++; // Keep track of how many we've placed
  }

  @Override
  public String toString() {
    return String.format("[state: %s, placed: %d/%d]", state, placedPieces, totalPieces);
  }
}
