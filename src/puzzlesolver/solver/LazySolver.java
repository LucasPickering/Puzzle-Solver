package puzzlesolver.solver;

import java.util.HashMap;
import java.util.Map;

import puzzlesolver.Piece;
import puzzlesolver.enums.Direction;

/**
 * A lazy solver is one that tries to put in easiest piece next, rather than by simply placing
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
public class LazySolver extends PieceTypeRotationSolver {

  private Map<Piece, Float> pieceCache = new HashMap<>();

  /**
   * {@inheritDoc}
   */
  @Override
  protected void placeNextPiece(State state) {
    for (int x = 0; x < state.width(); x++) {
      for (int y = 0; y < state.height(); y++) {
        // If there is no piece here, but there's an adjacent piece...
        if (state.solution[x][y] == null && adjacentCount(state, x, y) > 0) {
          // Calculate the potential piece for this spot
          final Piece madePiece = makePiece(state, x, y);
          pieceCache.put(madePiece, difficultyScore(madePiece, state));
        }
      }
    }

    final Piece easiestPiece = getEasiestPiece();
    Piece foundPiece = state.unplacedPieces.find(easiestPiece);
    int rotations;
    // Look for matches, and rotates the piece up to 3 times if no matches are found
    for (rotations = 0; foundPiece == null && rotations < Direction.values().length - 1;
         rotations++, foundPiece = state.unplacedPieces.find(easiestPiece)) {
      easiestPiece.rotate(Direction.NORTH, Direction.EAST); // If no matches were found, rotate once
    }

		/*
     * There's a chance that the "wrong" first corner was placed, and the puzzles is rotated 90
		 * degrees from what it should be. If the puzzle is non-square, it won't fit. We assume that
		 * when we can't find a specific piece, that it's because we're looking for an edge piece
		 * that shouldn't actually be an edge piece. This means we've reached the end of the array
		 * before we should've, and that the puzzle is rotated 90 degrees. Re-allocate the array with
		 * reversed dimensions, and we're good to go.
		 */
    if (foundPiece == null) { // If it didn't find a piece
      // If the puzzle hasn't been rotated yet
      if (!rotated) {
        rotated = true;
        rotateSolution(state); // Rotate the puzzle (see comment above)
        placeNextPiece(state); // Call again with the newly-rotated solution
        return;
      }
      throw new IllegalStateException(String.format("No piece found to go at (%d, %d)",
                                                    state.x, state.y));
    }

    foundPiece.rotate(Direction.values()[rotations], Direction.NORTH); // Rotate it back
    state.placePiece(foundPiece); // Put it in the solution
    state.unplacedPieces.remove(foundPiece); // Remove it from the list of unplaced pieces
    pieceCache.clear(); // TODO: Only evict pieces whose adjacent pieces changed
  }

  private int adjacentCount(State state, int x, int y) {
    int count = 0;
    for (Direction dir : Direction.values()) {
      if (state.solution[x + dir.x][y + dir.y] != null) {
        count++;
      }
    }
    return count;
  }

  private Piece getEasiestPiece() {
    Piece easiestPiece = null;
    float minScore = Float.MAX_VALUE;
    for (Map.Entry<Piece, Float> entry : pieceCache.entrySet()) {
      final float score = entry.getValue();
      if (entry.getValue() < minScore) {
        easiestPiece = entry.getKey();
        minScore = score;
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
  protected float difficultyScore(Piece foundPiece, State state) {
    return 1f / foundPiece.getPieceTypes().length; // More possible types, harder it is
  }
}
