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
      final int oldWidth = state.solution.length; // Might need these later
      final int oldHeight = state.solution[0].length;
      if (rotateSolutionIfHelpful(state)) {
        rebuildCacheAfterRotation(state, oldWidth, oldHeight); // Replace now-invalid cached pieces
        placeNextPiece(state); // Call again with the newly-rotated solution
        return;
      }
      throw new PieceNotFoundException(state.x, state.y);
    }

    foundPiece.rotate(Direction.values()[rotations], Direction.NORTH); // Rotate it back
    placePiece(state, foundPiece);
  }

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
      final Coord coord = new Coord(state.x + dir.x, state.y + dir.y);
      // Cache the adjacent piece, if it's in bounds
      if (Funcs.coordsInBounds(state.width(), state.height(), coord.x, coord.y)
          && state.solution[coord.x][coord.y] == null) {
        cachePiece(state, coord);
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
   * After the puzzle has been rotated, replace some invalid cached pieces. Invalid pieces (ones
   * that were on the edge and aren't now, or vice versa) are evicted, then new values are
   * inserted for all the pieces that were evicted.
   *
   * @param state     the current state of the puzzle
   * @param oldWidth  the width of the puzzle before the rotation
   * @param oldHeight the height of the puzzle before the rotation
   */
  private void rebuildCacheAfterRotation(State state, int oldWidth, int oldHeight) {
    final int newWidth = state.solution.length;
    final int newHeight = state.solution[0].length;
    for (Map.Entry<Coord, Pair<Piece, Float>> entry : madePieceCache.entrySet()) {
      final Coord coord = entry.getKey();
      if (coord.x == oldWidth - 1 || coord.x == newWidth - 1
          || coord.y == oldHeight - 1 || coord.y == newHeight - 1) {
        cachePiece(state, coord);
      }
    }
  }

  /**
   * Cache a potential piece for the given (x, y).
   *
   * @param state the current state of the puzzle
   * @param coord the coord of the piece to be cached
   */
  private void cachePiece(State state, Coord coord) {
    final Piece piece = makePiece(state, coord.x, coord.y); // Re-make the piece
    madePieceCache.put(coord, new Pair<>(piece, scorePiece(state, piece)));
  }

  /**
   * Gets a score representing how difficult the given piece will be to place in the solution. A
   * lower score means it will be easier, higher score means it will be more difficult.
   *
   * @param foundPiece the piece trying to be placed
   * @param state      the current state of the puzzle/solver
   * @return the difficulty score
   */
  protected float scorePiece(State state, Piece foundPiece) {
    int totalPotentialMatches = 0;
    for (PieceType pieceType : foundPiece.getPieceTypes()) {
      totalPotentialMatches += state.unplacedPieces.sublistByType(pieceType).size();
    }
    return 1f / totalPotentialMatches;
  }
}
