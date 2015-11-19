package puzzlesolver.simple;

import puzzlesolver.AbstractSolver;
import puzzlesolver.constants.Constants;
import puzzlesolver.Piece;
import puzzlesolver.Point;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;

public final class SimpleSolver extends AbstractSolver {

  private int x;
  private int y;

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

  /**
   * Makes a piece as accurately as possible to fit at the given x and y, using the pieces adjacent
   * to that spot.
   *
   * @param x the x-coord of the piece [0, width)
   * @param y the y-coord of the piece [0, height)
   * @return the constructed piece
   */
  private Piece makePiece(int x, int y) {
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
}
