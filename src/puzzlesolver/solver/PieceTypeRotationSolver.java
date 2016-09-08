package puzzlesolver.solver;

import puzzlesolver.Piece;
import puzzlesolver.enums.PieceType;
import puzzlesolver.piecelist.PieceList;
import puzzlesolver.piecelist.PieceTypePieceList;

public class PieceTypeRotationSolver extends RotationSolver {

  @Override
  public void init(Piece[] pieces) {
    PieceList unplacedPieces = new PieceTypePieceList(pieces.length);
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
}
