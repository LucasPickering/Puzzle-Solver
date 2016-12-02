package puzzlesolver.solver;

import puzzlesolver.Piece;
import puzzlesolver.piecelist.PieceList;
import puzzlesolver.piecelist.PieceTypePieceList;

public class PieceTypeRotationSolver extends RotationSolver {

    @Override
    protected PieceList makePieceList(Piece[] pieces) {
        return new PieceTypePieceList(pieces.length);
    }
}
