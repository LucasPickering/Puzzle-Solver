package puzzlesolver.solver;

import puzzlesolver.Piece;

public interface Solver {

  /**
   * Solves the given array of pieces, creating a 2-D array of Pieces in their proper places.
   * @param pieces the pieces that make up the puzzle, in random order
   * @return a 2-D array of the pieces, in their proper places and orientations.
   */
  Piece[][] solve(Piece[] pieces);

}
