package puzzlesolver.rotation;

import puzzlesolver.AbstractSolver;
import puzzlesolver.Piece;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;

public class RotationSolver extends AbstractSolver {

  /**
   * Keeps track of whether the puzzle has been rotated once.
   */
  private boolean rotated;

  @Override
  public boolean nextStep() {
    // If this is the first piece, find the first corner in the list and place it
    if (x == 0 && y == 0) {
      for (int i = 0; i < unplacedPieces.size(); i++) {
        Piece piece = unplacedPieces.get(Direction.NORTH, i);

        // For the first corner piece found
        if (piece.definitelyType(PieceType.CORNER)) {
          // Rotate it until it fits in the top-left corner
          while (!piece.getSide(Direction.NORTH).isFlat() ||
                 !piece.getSide(Direction.WEST).isFlat()) {
            piece.rotate(Direction.NORTH, Direction.EAST);
          }
          solution[x][y] = piece;
          unplacedPieces.remove(piece);
          break;
        }
      }
    } else if (x < width && y < height) {
      final Piece madePiece = makePiece(x, y);
      Piece foundPiece;
      int rotations;
      // Looks for matches, and rotates the piece up to 3 times if no matches are found
      for (rotations = 1; (foundPiece = unplacedPieces.find(madePiece)) == null &&
                          rotations < Direction.values().length; rotations++) {
        madePiece.rotate(Direction.NORTH, Direction.EAST); // If no matches were found, rotate once
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
          rotateSolution();
          rotated = true;
          return nextStep();
        }
        throw new IllegalStateException(String.format("No piece found to go at (%d, %d)", x, y));
      }

      foundPiece.rotate(Direction.NORTH, Direction.values()[rotations - 1]);
      solution[x][y] = foundPiece;
      unplacedPieces.remove(foundPiece);
    }
    if (++x >= width) { // Increment x, if the row is done, go to the next row
      x = 0;
      y++;
    }
    return y >= height; // If we're past the last row, we're done
  }

  /**
   * Re-allocates the solution array with reversed dimensions. The old width is the new height, and
   * vice versa. Puts all the old pieces in the new array without changing their positions at all.
   */
  private void rotateSolution() {
    Piece[][] newSolution = new Piece[solution[0].length][];
    for (int x = 0; x < newSolution.length; x++) {
      newSolution[x] = new Piece[solution.length];
      for (int y = 0; y < newSolution[0].length; y++) {
        if (x >= solution.length || y >= solution[0].length) {
          return;
        }
        newSolution[x][y] = solution[x][y];
      }
    }
    solution = newSolution;
  }
}
