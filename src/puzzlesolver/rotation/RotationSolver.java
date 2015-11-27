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
      // Looks for matches, and rotates the piece up to 3 times if no matches are found
      for (int i = 1; (foundPiece = unplacedPieces.find(madePiece)) == null &&
                      i < Direction.values().length; i++) {
        madePiece.rotate(Direction.NORTH, Direction.EAST); // If no matches were found, rotate once
      }
      if (foundPiece == null && !rotated) {
        rotateSolution();
        rotated = true;
      }
      solution[x][y] = foundPiece;
      unplacedPieces.remove(foundPiece);
    }
    if (++x >= width) { // Increment x, if the row is done, go to the next row
      x = 0;
      y++;
    }
    return y >= height; // If we're past the last row, we're done
  }
}
