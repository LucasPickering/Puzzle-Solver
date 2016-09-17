package puzzlesolver.solver;

import puzzlesolver.Piece;
import puzzlesolver.PieceNotFoundException;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;
import puzzlesolver.piecelist.PieceList;
import puzzlesolver.piecelist.SimplePieceList;

public class SimpleSolver extends AbstractSolver {

  @Override
  protected void placeCorner(State state) throws PieceNotFoundException {
    for (int i = 0; i < state.unplacedPieces.size(); i++) {
      Piece piece = state.unplacedPieces.get(Direction.NORTH, i);
      if (piece.definitelyType(PieceType.CORNER) && piece.getSide(Direction.NORTH).isFlat() &&
          piece.getSide(Direction.WEST).isFlat()) {
        placePiece(state, piece); // Put the piece in the solution
        break;
      }
    }
  }

  @Override
  protected void placeNextPiece(State state) throws PieceNotFoundException {
    final Piece piece = state.unplacedPieces.find(makePiece(state));
    placePiece(state, piece); // Put the piece in the solution
  }

  @Override
  protected PieceList makePieceList(Piece[] pieces) {
    return new SimplePieceList(pieces.length);
  }
}
