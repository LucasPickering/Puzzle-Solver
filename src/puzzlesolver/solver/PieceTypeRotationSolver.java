package puzzlesolver.solver;

import puzzlesolver.Piece;
import puzzlesolver.enums.PieceType;
import puzzlesolver.piecelist.PieceTypePieceList;

public class PieceTypeRotationSolver extends RotationSolver {

  @Override
  public void init(Piece[] pieces) {
    unplacedPieces = new PieceTypePieceList();
    int edges = 0;
    for (Piece piece : pieces) {
      if (piece.definitelyType(PieceType.EDGE)) {
        edges++;
      }
      unplacedPieces.add(piece);
    }

    width = getWidth(edges + 4, pieces.length);
    height = getHeight(width, pieces.length);
    solution = new Piece[width][height];
  }
}
