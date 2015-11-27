package puzzlesolver.simple;

import puzzlesolver.AbstractSolver;
import puzzlesolver.Piece;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;

public class SimpleSolver extends AbstractSolver {

  @Override
  public boolean nextStep() {
    // If this is the first piece, find the first corner in the list and place it
    if (x == 0 && y == 0) {
      for (int i = 0; i < unplacedPieces.size(); i++) {
        Piece piece = unplacedPieces.get(Direction.NORTH, i);
        if (piece.definitelyType(PieceType.CORNER) && piece.getSide(Direction.NORTH).isFlat() &&
            piece.getSide(Direction.WEST).isFlat()) {
          solution[x][y] = piece;
          unplacedPieces.remove(piece);
          break;
        }
      }
    } else if (x < width && y < height) {
      final Piece piece = unplacedPieces.find(makePiece(x, y));
      solution[x][y] = piece;
      unplacedPieces.remove(piece);
    }
    if (++x >= width) { // Increment x, if the row is done, go to the next row
      x = 0;
      y++;
    }
    return y >= height; // If we're past the last row, we're done
  }
}
