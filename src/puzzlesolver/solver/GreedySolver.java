package puzzlesolver.solver;

import java.util.HashMap;
import java.util.Map;

import puzzlesolver.Coord;
import puzzlesolver.Funcs;
import puzzlesolver.Pair;
import puzzlesolver.Piece;
import puzzlesolver.PieceNotFoundException;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;

/**
 * A greedy solver is one that tries to put in easiest piece next, rather than by simply placing
 * the next piece in line. The idea is that, by using a relatively quick method to
 * determine which piece is easiest, you can fill out the puzzle as quickly as possible.
 *
 * This accomplishes two different things that both help later pieces be placed. First, you take
 * another piece out of the bag of potential pieces, meaning you have fewer pieces to look
 * through in the future. Second, there are more pieces in the puzzle, which gives you more edges
 * in the puzzle to work with, making it easier to figure out what piece you're looking for.
 *
 * The inspiration for this method is from how people tend to solve jigsaw puzzles. First, you
 * start with the corners/edges, because there are so few of them, so it makes them easy to place
 * and it immediately gives you a few hundred edges in the puzzle to connect to. Then, you tend
 * to look for a piece that's easy to find, so that you can cut down on the amount of pieces you
 * have to look for.
 */
public class GreedySolver extends PieceTypeRotationSolver {

  private Map<Coord, Pair<Piece, Float>> madePieceCache = new HashMap<>();


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean nextStep() throws PieceNotFoundException {
    // If this is the first piece, find the first corner in the list and place it
    if (state.solution[0][0] == null) {
      placeCorner(state); // Place the first piece in the puzzle
    } else {
      placeNextPiece(state); // Place the next piece in the puzzle
    }
    return done(state); // Return false if we need to keep going, true if we're done
  }

  @Override
  protected void placeCorner(State state) {
    for (int i = 0; i < state.unplacedPieces.size(); i++) {
      Piece piece = state.unplacedPieces.get(Direction.NORTH, i);

      // For the first corner piece found
      if (piece.definitelyType(PieceType.CORNER)) {
        // Rotate it until it fits in the top-left corner
        while (!piece.getSide(Direction.NORTH).isFlat()
               || !piece.getSide(Direction.WEST).isFlat()) {
          piece.rotate(Direction.NORTH, Direction.EAST);
        }
        placePiece(state, piece);
        return; // We placed it, done!
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void placeNextPiece(State state) throws PieceNotFoundException {
    final Map.Entry<Coord, Pair<Piece, Float>> easiestPieceEntry = getEasiestPiece();
    state.x = easiestPieceEntry.getKey().x;
    state.y = easiestPieceEntry.getKey().y;
    final Piece easiestPiece = easiestPieceEntry.getValue().left;
    Piece foundPiece = state.unplacedPieces.find(easiestPiece);
    int rotations;
    // Look for matches, and rotat the piece up to 3 times if no matches are found
    for (rotations = 0; foundPiece == null && rotations < Direction.values().length - 1;
         rotations++) {
      easiestPiece.rotate(Direction.NORTH, Direction.EAST); // If no matches were found, rotate once
      foundPiece = state.unplacedPieces.find(easiestPiece); // Try again
    }

    if (foundPiece == null) { // If it didn't find a piece
      if (rotateSolutionIfHelpful(state)) {
        placeNextPiece(state); // Call again with the newly-rotated solution
        return;
      }
      throw new PieceNotFoundException(state.x, state.y);
    }

    foundPiece.rotate(Direction.values()[rotations], Direction.NORTH); // Rotate it back
    placePiece(state, foundPiece);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean done(State state) {
    return state.unplacedPieces.size() == 0;
  }

  private void placePiece(State state, Piece piece) {
    state.placePiece(piece); // Put the piece in the solution
    state.unplacedPieces.remove(piece);
    // Evict the piece that changed, then re-cache the adjacent pieces
    madePieceCache.remove(new Coord(state.x, state.y));
    for (Direction dir : Direction.values()) {
      final Coord dirCoord = new Coord(state.x + dir.x, state.y + dir.y);
      madePieceCache.remove(dirCoord); // No need to check for bounds or membership
      // Cache the adjacent piece, if it's in bounds
      if (Funcs.coordsInBounds(state.width(), state.height(), dirCoord.x, dirCoord.y)
          && state.solution[dirCoord.x][dirCoord.y] == null) {
        final Piece newPiece = makePiece(state, dirCoord.x, dirCoord.y); // Re-make the piece
        madePieceCache.put(dirCoord, new Pair<>(newPiece, scorePiece(newPiece, state)));
      }
    }
  }

  private Map.Entry<Coord, Pair<Piece, Float>> getEasiestPiece() {
    Map.Entry<Coord, Pair<Piece, Float>> easiestPiece = null;
    for (Map.Entry<Coord, Pair<Piece, Float>> entry : madePieceCache.entrySet()) {
      if (easiestPiece == null || entry.getValue().right < easiestPiece.getValue().right) {
        easiestPiece = entry;
      }
    }
    return easiestPiece;
  }

  /**
   * Gets a score [0, 1] representing how difficult the given piece will be to place in the
   * solution. A lower score means it will be easier, higher score means it will be more difficult.
   *
   * @param foundPiece the piece trying to be placed
   * @param state      the current state of the puzzle/solver
   * @return the difficulty score [0, 1]
   */
  protected float scorePiece(Piece foundPiece, State state) {
    return 1f / foundPiece.getPieceTypes().length; // TODO: Better metric
  }
}
