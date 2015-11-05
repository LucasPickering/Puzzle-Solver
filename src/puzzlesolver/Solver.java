package puzzlesolver;

public interface Solver {

  /**
   * Initializes this solver to use the given array of pieces.
   *
   * @param pieces the pieces that make up the puzzle, in any order
   */
  void init(Piece[] pieces);

  /**
   * Executes the next step in the solver, placing exactly one piece.
   */
  void nextStep();

  /**
   * Gets the list of pieces that have not yet been placed into the solution. No copying is done, so
   * <i>please</i> don't screw with this object! Read only!
   *
   * @return all unplaced pieces
   */
  PieceList getUnplacedPieces();

  /**
   * Gets this solver's solution in its current place. All pieces in the solution will be in their
   * correct spot, with null spots for unplaced pieces. No copying is done, so <i>please</i> don't
   * screw with this object! Read only!
   *
   * @return a 2-D array representing all pieces that have been placed, in their final spots
   */
  Piece[][] getSolution();
}
